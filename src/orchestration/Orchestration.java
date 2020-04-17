package orchestration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.AbstractChannel;
import pubSubServer.ChannelDiscovery;
import pubSubServer.SubscriptionManager;
import publishers.AbstractPublisher;
import publishers.PublisherFactory;
import states.subscriber.StateName;
import strategies.publisher.StrategyName;
import subscribers.AbstractSubscriber;
import subscribers.SubscriberFactory;

public class Orchestration {

	public static void main(String[] args) {

		HashMap<Integer, AbstractPublisher> listOfPublishers = new HashMap<Integer, AbstractPublisher>();
		HashMap<Integer, AbstractSubscriber> listOfSubscribers = new HashMap<Integer, AbstractSubscriber>();
		Orchestration testHarness = new Orchestration();
		try {
			listOfPublishers = testHarness.createPublishers();
			listOfSubscribers = testHarness.createSubscribers();
			List<AbstractChannel> channels = ChannelDiscovery.getInstance().listChannels();
			// For demonstration purposes only
			try {
				BufferedReader initialChannels = new BufferedReader(new FileReader(new File("Channels2.chl")));
				List<String> channelList = new ArrayList<String>();
				String line = "";
				while ((line = initialChannels.readLine()) != null)
					channelList.add(line);
				int subscriberIndex = 0;
				for (int i = 0; i < listOfSubscribers.size(); i++) {
					AbstractSubscriber subscriber = listOfSubscribers.get(i);
					subscriber.subscribe(channelList.get(subscriberIndex % channelList.size()));
					subscriberIndex++;
				}
				initialChannels.close();
			} catch (IOException ioe) {
				System.out.println("Loading Channels from file failed proceeding with random selection");
				for (int i = 0; i < listOfSubscribers.size(); i++) {
					AbstractSubscriber subscriber = listOfSubscribers.get(i);
					int index = (int) Math.round((Math.random() * 3));
					SubscriptionManager.getInstance().subscribe(channels.get(index).getChannelTopic(), subscriber);
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			System.out.println("Will now terminate");
			return;
		}

		String useCase = "UseCase5";

		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(new File(useCase)));
			while (lineReader.ready()) {
				String line = lineReader.readLine();
				String[] command = line.split("\t");
				switch (command[0]) {
				case "PUB":
					int pubId = Integer.parseInt(command[1]);
					if (command.length == 2) {
						listOfPublishers.get(pubId).publish();
					} else {
						int eventType = Integer.parseInt(command[2]);
						listOfPublishers.get(pubId).publish(EventFactory.createEvent(EventType.values()[eventType],
								pubId, new EventMessage(command[3], command[4])));
						;
					}
					break;
				case "SUB":
					int subId = Integer.parseInt(command[1]);
					SubscriptionManager.getInstance().subscribe(command[2], listOfSubscribers.get(subId));
					break;
				case "BLOCK":
					int subID = Integer.parseInt(command[1]);
					ChannelDiscovery.getInstance().findChannel(command[2]).block(listOfSubscribers.get(subID));
					break;
				case "UNBLOCK":
					int subsID = Integer.parseInt(command[1]);
					ChannelDiscovery.getInstance().findChannel(command[2]).unBlock(listOfSubscribers.get(subsID));
					break;
				}
			}
			lineReader.close();
		} catch (Exception e) {
			System.out.println("\n Problem reading in Use Case");
		}

	}

	private HashMap<Integer, AbstractPublisher> createPublishers() throws IOException {
		HashMap<Integer, AbstractPublisher> listOfPublishers = new HashMap<Integer, AbstractPublisher>();
		AbstractPublisher newPub;
		BufferedReader StrategyBufferedReader = new BufferedReader(new FileReader(new File("Strategies2.str")));
		while (StrategyBufferedReader.ready()) {
			String PublisherConfigLine = StrategyBufferedReader.readLine();
			String[] PublisherConfigArray = PublisherConfigLine.split("\t");
			int[] PublisherConfigIntArray = new int[2];
			for (int i = 0; i < PublisherConfigArray.length; i++)
				PublisherConfigIntArray[i] = Integer.parseInt(PublisherConfigArray[i]);
			if (!listOfPublishers.containsKey(PublisherConfigIntArray[0])) {
				newPub = PublisherFactory.createPublisher(PublisherConfigIntArray[0],
						StrategyName.values()[PublisherConfigIntArray[1]]);
				listOfPublishers.put(PublisherConfigIntArray[0], newPub);
			} else {
				PublisherFactory.createPublisher(PublisherConfigIntArray[0],
						StrategyName.values()[PublisherConfigIntArray[1]]);
			}
		}
		StrategyBufferedReader.close();
		return listOfPublishers;
	}

	private HashMap<Integer, AbstractSubscriber> createSubscribers() throws IOException {
		HashMap<Integer, AbstractSubscriber> listOfSubscribers = new HashMap<Integer, AbstractSubscriber>();
		AbstractSubscriber newSub;
		BufferedReader StateBufferedReader = new BufferedReader(new FileReader(new File("States2.sts")));
		while (StateBufferedReader.ready()) {
			String StateConfigLine = StateBufferedReader.readLine();
			String[] StateConfigArray = StateConfigLine.split("\t");
			int[] StateConfigIntArray = new int[2];
			for (int i = 0; i < StateConfigArray.length; i++)
				StateConfigIntArray[i] = Integer.parseInt(StateConfigArray[i]);
			if (!listOfSubscribers.containsKey(StateConfigIntArray[0])) {
				newSub = SubscriberFactory.createSubscriber(StateConfigIntArray[0],
						StateName.values()[StateConfigIntArray[1]]);
				listOfSubscribers.put(StateConfigIntArray[0], newSub);
			} else {
				listOfSubscribers.get(StateConfigIntArray[0]).setState(StateName.values()[StateConfigIntArray[1]]);
			}
		}
		StateBufferedReader.close();
		return listOfSubscribers;
	}

}

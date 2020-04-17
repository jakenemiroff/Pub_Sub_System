package events;

class EventTypeD extends AbstractEvent {

	protected EventTypeD(long eventID, int eventPublisherId, EventMessage payload) {
		super(eventID, eventPublisherId, payload);
	}

}

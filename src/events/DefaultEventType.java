package events;

class DefaultEventType extends AbstractEvent {

	protected DefaultEventType(long eventID, int eventPublisherId, EventMessage payload) {
		super(eventID, eventPublisherId, payload);
	}

}

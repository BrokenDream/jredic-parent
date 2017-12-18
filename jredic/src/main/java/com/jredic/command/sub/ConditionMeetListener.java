package com.jredic.command.sub;

/**
 * Listener class for set NX/XX condition meeting.
 *
 * @author David.W
 */
public interface ConditionMeetListener {

    /**
     * on the meet result.
     *
     * @param isMeet true if condition is met,otherwise false.
     */
    void onMeet(boolean isMeet);

}

package utility;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * Stores each path used during autonomous
 */
public class WaypointStore {

	public final static Waypoint[] pointsTurn = new Waypoint[] {
	    new Waypoint(0, 7, 0),
	    new Waypoint(5, 7, 0),
	    new Waypoint(8, 8, Pathfinder.d2r(45)),
	    new Waypoint(8, 12, Pathfinder.d2r(90)),
	    new Waypoint(8, 14, Pathfinder.d2r(90))
		//new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
	    //new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
	    //new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
	};
	
	public final static Waypoint[] pointsOnlyX = new Waypoint[] {
		new Waypoint(0, 0, 0),
		new Waypoint(5, 0, 0)
	};
	
	public final static Waypoint[] pointsOnlyY = new Waypoint[] {
			new Waypoint(0, 0, 90),
			new Waypoint(0, 5, 90)
	};
	
	public final static Waypoint[] longPath = new Waypoint[] {
		new Waypoint(0,5,0),
		new Waypoint(2,5,0),
		new Waypoint(5,10,Pathfinder.d2r(90)),
		new Waypoint(5,20,Pathfinder.d2r(90)),
		new Waypoint(8,22,0),
		new Waypoint(16,22,0),
		new Waypoint(19,20,Pathfinder.d2r(-90)),
		new Waypoint(19,15,Pathfinder.d2r(-90)),
		new Waypoint(17,12,Pathfinder.d2r(180))
	};

}

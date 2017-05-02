package editors;
public class Buff
{
    private BuffType buffType;
    private String name;
    private int value;
    private double duration;
    private double timeElapsed;
    private double interval;
    private double intervalTimeElapsed;
    private boolean takeEffect;

    public Buff ( String name, BuffType buffType, int value, double duration, double interval ) {
        this.name = name;
    	this.buffType = buffType;
        this.value = value;
        this.duration = duration;
        this.interval = interval;

        if ( interval == 0 )
            takeEffect = true;
        else
            takeEffect = false;

        timeElapsed = 0.0;
        intervalTimeElapsed = 0.0;
    }

    public BuffType getBuffType () { return buffType; }
    public int getValue () { return value; }
    public boolean takeEffect () { return takeEffect; }
    public void takenEffect () { takeEffect = false; }

    public int getTicks () {
        if ( interval != 0.0 )
            return (int) (duration / interval);
        else
            return 1;
    }


    public boolean expired () { return timeElapsed >= duration; }

    public void update ( double deltaTime ) {
        timeElapsed += deltaTime;
        intervalTimeElapsed += deltaTime;

        if ( interval != 0.0 ) {
            if ( intervalTimeElapsed >= interval ) {
                takeEffect = true;
                intervalTimeElapsed -= interval;
            }
        }
    }
    
    @Override
    public String toString() {
    	if ( interval == 0 ) {
    		return String.format("%s: %s by %d for %.3f second(s)\n", name, buffType.name(), value, duration);
    	}
    	
    	if ( duration == 0 ) {
    		return String.format("%s: %s by %d\n", name, buffType.name(), value);
    	}
    	
    	return String.format("%s: %s by %d every %.3f second(s) for %.3f second(s)\n", name, buffType.name(), value, interval, duration);
    }
}

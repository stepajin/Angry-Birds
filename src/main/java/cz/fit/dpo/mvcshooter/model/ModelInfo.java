package cz.fit.dpo.mvcshooter.model;

/**
 *
 * @author Ondrej Stuchlik
 */
public class ModelInfo {
    public int cannonForce;
    public int cannonAngle;
    public int gravity;
    public int score;
    public String shootingMode;
    public String gameMode;
    
    public ModelInfo() {
    }

    public ModelInfo(int cannonForce, int cannonAngle, int gravity, int score, String shootingMode, String gameMode) {
        this.cannonForce = cannonForce;
        this.cannonAngle = cannonAngle;
        this.gravity = gravity;
        this.score = score;
        this.shootingMode = shootingMode;
        this.gameMode = gameMode;
    }
    
    
}

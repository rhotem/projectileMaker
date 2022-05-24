package me.justacat.projectilemaker;

import org.bukkit.Particle;

public class Projectile {

    //general settings
    private String name;
    private String type = "Beam";

    private double range = 20.0;
    private double velocity = 10.0;
    private Particle particle = Particle.FLAME; //need to check if i can json that
    private int delay = 1;

    //spiral

    private int branches;
    private double angle;

    //cone



    public Projectile(String name) {

        this.name = name;
        FileManager.createJSON(name, FileManager.projectilesFolder, this, true);

    }


    public void setType(String type) {this.type = type;}
    public void setVelocity(double velocity) {this.velocity = velocity;}
    public void setDelay(int delay) {this.delay = delay;}
    public void setParticle(Particle particle) {this.particle = particle;}
    public void setRange(double range) {this.range = range;}
    public void setAngle(double angle) {this.angle = angle;}
    public void setBranches(int branches) {this.branches = branches;}

    public int getDelay() {return delay;}
    public double getAngle() {return angle;}
    public double getRange() {return range;}
    public double getVelocity() {return velocity;}
    public int getBranches() {return branches;}
    public String getType() {return type;}
    public Particle getParticle() {return particle;}



}

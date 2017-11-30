package com.thegame.model.weapon;

public class WeaponSpec {
    public int price; // initial or next level
    public int power;
    public int speed; // fires per minute
    public double distance;
    public int special;
    public int bulletSpeed; // 0 - momentary
    public double rotationSpeed; // rads per second

    public long rechargeTime;

    public WeaponSpec(int price, int power, int speed, double distance, int special, int bulletSpeed, double rotationSpeed) {
        this.price = price;
        this.power = power;
        this.speed = speed;
        this.distance = distance;
        this.special = special;
        this.bulletSpeed = bulletSpeed;
        this.rotationSpeed = rotationSpeed;

        rechargeTime = 60_000 / speed;
    }

}

public class Player {

    int hp = 100;
    int maxHp = 100;
    int attack = 10;
    int defense = 50;
    int level = 1;
    int xp = 1;
    int gold = 500;
    int armorDamage = 3;

    public Player(){
    }

    public int takeDamage(int incomingAttack, int armorDamage){
        int damage = Math.max(1, incomingAttack - defense);
        //defense = defense - incomingattack;
        hp -= damage;
        defense = Math.max(0,defense - armorDamage);
        return damage;
    }
    public boolean isAlive(){
        return hp > 0;
    }

    public String getStats(){
        return String.format("HP: %d/%d | Level: %d | XP: %d | Gold: %d", hp, maxHp, level, xp, gold);
    }

}

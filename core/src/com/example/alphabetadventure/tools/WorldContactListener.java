package com.example.alphabetadventure.tools;



import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.sprites.endoflevel.BabyLetter;
import com.example.alphabetadventure.sprites.endoflevel.Catapult;
import com.example.alphabetadventure.sprites.endoflevel.Plank;
import com.example.alphabetadventure.sprites.enemies.Enemy;
import com.example.alphabetadventure.sprites.items.CollectFireballs;
import com.example.alphabetadventure.sprites.items.NextLetter;
import com.example.alphabetadventure.sprites.items.PowerUp;
import com.example.alphabetadventure.sprites.tileobjects.InterActiveTileObject;

public class WorldContactListener implements ContactListener {
    public static boolean cantJump;
    @Override
    public void beginContact(Contact contact) {//begining of objects contacting
        Fixture fixA = contact.getFixtureA();//we have two fixtures we need to chek if one is letter head object
        Fixture fixb = contact.getFixtureB();
//cdef collision def if mario collides with ground it would be 0011?
        int cDef = fixA.getFilterData().categoryBits| fixb.getFilterData().categoryBits;





        switch (cDef) {

            case MainClass.LETTER_HEAD_BIT | MainClass.BOX_BIT:
                if (fixA.getFilterData().categoryBits == MainClass.LETTER_HEAD_BIT)
                    ((InterActiveTileObject) fixb.getUserData()).onHeadHit((Letter) fixA.getUserData());
                else
                    ((InterActiveTileObject) fixA.getUserData()).onHeadHit((Letter) fixb.getUserData());
                break;
            case MainClass.ENEMY_HEAD_BIT | MainClass.LETTER_BIT: //mario collides with enemy head
                if (fixA.getFilterData().categoryBits == MainClass.ENEMY_HEAD_BIT)//then we know fixa is enemy
                    ((Enemy) fixA.getUserData()).hitOnHead((Letter) fixb.getUserData());
                else
                    ((Enemy) fixb.getUserData()).hitOnHead((Letter) fixA.getUserData());
                break;
            case MainClass.ENEMY_BIT | MainClass.OBJECT_BIT: //mario collides with enemy head
                if (fixA.getFilterData().categoryBits == MainClass.ENEMY_BIT) {//then we know fixa is enemy
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);//

                } else {
                    ((Enemy) fixb.getUserData()).reverseVelocity(true, false);
                }
                break;

            case MainClass.LETTER_BIT | MainClass.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MainClass.LETTER_BIT)
                    ((Letter) fixA.getUserData()).hit((Enemy) fixb.getUserData());
                else
                    ((Letter) fixb.getUserData()).hit((Enemy) fixA.getUserData());
                break;


            case MainClass.ENEMY_BIT | MainClass.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).hitByEnemy((Enemy) fixb.getUserData());//if enemy collide they should reverse directio
                ((Enemy) fixb.getUserData()).hitByEnemy((Enemy) fixA.getUserData());//and other enemy should reverse
                break;
            case MainClass.COLLECT_FIREBALL_BIT | MainClass.OBJECT_BIT: //mario collides with enemy head
                if (fixA.getFilterData().categoryBits == MainClass.COLLECT_FIREBALL_BIT) {//then we know fixa is enemy
                    ((CollectFireballs) fixA.getUserData()).reverseVelocity(true, false);//

                } else{
                    ((CollectFireballs) fixb.getUserData()).reverseVelocity(true, false);
                }
                break;
            case MainClass.COLLECT_FIREBALL_BIT | MainClass.LETTER_BIT: //if mario collides with item
                if(fixA.getFilterData().categoryBits == MainClass.COLLECT_FIREBALL_BIT)//is fixture a the collected item
                    ((CollectFireballs)fixA.getUserData()).use((Letter) fixb.getUserData());//if its then we use on letter
                else
                    ((CollectFireballs)fixb.getUserData()).use((Letter) fixA.getUserData());//other wise fixture fixb must be item then we want to use it on fix a wich must be letter
                break;
            case MainClass.NEXTLETTER_BIT | MainClass.OBJECT_BIT: //mario collides with enemy head
                if (fixA.getFilterData().categoryBits == MainClass.NEXTLETTER_BIT) {//then we know fixa is enemy
                    ((NextLetter) fixA.getUserData()).reverseVelocity(true, false);//

                } else{
                    ((NextLetter) fixb.getUserData()).reverseVelocity(true, false);
                }
                break;
            case MainClass.NEXTLETTER_BIT | MainClass.LETTER_BIT: //if mario collides with item
                if(fixA.getFilterData().categoryBits == MainClass.NEXTLETTER_BIT)//is fixture a the collected item
                    ((NextLetter)fixA.getUserData()).use((Letter) fixb.getUserData());//if its then we use on letter
                else
                    ((NextLetter)fixb.getUserData()).use((Letter) fixA.getUserData());//other wise fixture fixb must be item then we want to use it on fix a wich must be letter
                break;
            case MainClass.LETTER_BIT | MainClass.OBJECT_BIT: //mario collides with enemy head
                if (fixA.getFilterData().categoryBits == MainClass.LETTER_BIT) {//then we know fixa is enemy
                    ((Letter) fixA.getUserData()).setOrigin(0.2f,0.3f);

                    if(Letter.runningRight1) {

                        ((Letter) fixA.getUserData()).setRotation(32);
                    }else {
                        ((Letter) fixA.getUserData()).setRotation(-32);

                    }
                } else{
                    ((Letter) fixb.getUserData()).setOrigin(0.15f,0.2f);

                    if(Letter.runningRight1) {
                        ((Letter) fixb.getUserData()).setRotation(32);
                    }else{
                        ((Letter) fixb.getUserData()).setRotation(-32);

                    }
                }
                break;
            case MainClass.POWER_UP_BIT | MainClass.OBJECT_BIT: //mario collides with enemy head
                if (fixA.getFilterData().categoryBits == MainClass.POWER_UP_BIT) {//then we know fixa is enemy
                    ((PowerUp) fixA.getUserData()).reverseVelocity(true, false);//
                } else{
                    ((PowerUp) fixb.getUserData()).reverseVelocity(true, false);
                }
                break;
            case MainClass.POWER_UP_BIT | MainClass.LETTER_BIT: //if mario collides with item

                if(fixA.getFilterData().categoryBits == MainClass.POWER_UP_BIT) {//is fixture a the collected item
                    //remove the power up img
                    ((PowerUp) fixA.getUserData()).use((Letter) fixb.getUserData());//if its then we use on letter

                } else {
                    ((PowerUp) fixb.getUserData()).use((Letter) fixA.getUserData());//other wise fixture fixb must be item then we want to use it on fix a wich must be letter
                }

              /*  //makes letter speed up
                if(fixA.getFilterData().categoryBits == MainClass.LETTER_BIT){
                    ((Letter) fixA.getUserData()).speedUp();//if its then we use on letter
                }else
                    ((Letter)fixb.getUserData()).speedUp();//other wise fixture fixb must be item then we want to use it on fix a wich must be letter

*/

                break;


            /*case MainClass.PLANKS_BIT|MainClass.FIREBALL_BIT  :
                if(fixA.getFilterData().categoryBits == MainClass.FIREBALL_BIT) {
                //   ((FireBall) fixA.getUserData()).fireBallSetToDestroy();
                }else {
                 //   ((FireBall) fixb.getUserData()).fireBallSetToDestroy();

                }
                break;*/
            case MainClass.LETTER_BIT| MainClass.DOOR_BIT:
                if (fixA.getFilterData().categoryBits == MainClass.LETTER_BIT) {//then we know fixa is enemy
                    ((Letter) fixA.getUserData()).setToDestroy();
                } else{

                    ((Letter) fixb.getUserData()).setToDestroy();
                }
                break;
            case MainClass.BABY_LETTER_BIT | MainClass.DOOR_BIT: //mario collides with enemy head
                if (fixA.getFilterData().categoryBits == MainClass.BABY_LETTER_BIT) {//then we know fixa is enemy
                    ((BabyLetter) fixA.getUserData()).babyLetterSetToDestroy();//
                } else{
                    ((BabyLetter) fixb.getUserData()).babyLetterSetToDestroy();
                }
                break;
            case MainClass.FIREBALL_BIT | MainClass.CATAPULT_ARM_BIT: //if mario collides with item
                if(fixA.getFilterData().categoryBits == MainClass.CATAPULT_ARM_BIT)//is fixture a the collected item
                    ((Catapult)fixA.getUserData()).setIsLoaded(true);//if its then we use on letter
                else
                    ((Catapult)fixb.getUserData()).setIsLoaded(true);//other wise fixture fixb must be item then we want to use it on fix a wich must be letter
                break;


        }



    }





    @Override
    public void endContact(Contact contact) {//when objects end contacting
        Fixture fixA = contact.getFixtureA();//we have two fixtures we need to chek if one is letter head object
        Fixture fixb = contact.getFixtureB();
//cdef collision def if mario collides with ground it would be 0011?
        int cDef = fixA.getFilterData().categoryBits | fixb.getFilterData().categoryBits;

        switch (cDef) {
            case MainClass.LETTER_BIT | MainClass.OBJECT_BIT: //mario collides with enemy head
                if (fixA.getFilterData().categoryBits == MainClass.LETTER_BIT) {//then we know fixa is enemy
                    if (Letter.isRunningRight()) {
                        ((Letter) fixA.getUserData()).setOrigin(-0.2f, -0.3f);

                        ((Letter) fixA.getUserData()).setRotation(0);
                    } else {
                        ((Letter) fixb.getUserData()).setOrigin(-0.15f, -0.2f);


                        ((Letter) fixA.getUserData()).setRotation(0);

                    }

                } else {
                    if (Letter.isRunningRight()) {
                        ((Letter) fixb.getUserData()).setRotation(0);
                    } else {
                        ((Letter) fixb.getUserData()).setRotation(0);

                    }

                }
                break;
  case MainClass.FIREBALL_BIT | MainClass.CATAPULT_ARM_BIT: //if mario collides with item
                if(fixA.getFilterData().categoryBits == MainClass.CATAPULT_ARM_BIT)//is fixture a the collected item
                    ((Catapult)fixA.getUserData()).setIsLoaded(false);//if its then we use on letter
                else
                    ((Catapult)fixb.getUserData()).setIsLoaded(false);//other wise fixture fixb must be item then we want to use it on fix a wich must be letter
                break;




        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {//once something has colided you can change the characteristic of that collision

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {//give results of that collision

    }
}

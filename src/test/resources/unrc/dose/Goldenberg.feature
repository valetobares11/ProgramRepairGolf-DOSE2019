#Authors: Borda, Celi, de Prada, Goldenberg.

Scenario: Admin user wants to create a new valid test based challenge
  Given the user “root” is already logged on
  And the user has admin permission
  And the text of the challenge is
  “
    public class Module{
      public static int module(int a){
        if(a >= 0){
            return -a;
        }
        else{
          return a;
        }
      }
    }
  ”
  And the text of the tests is:
    “
    @Test
    public void test1(){
      Int value =5;
      Int result = Module.module(value);
      assertEquals(value,result);
    }
    @Test
    public void test2(){
      Int value = 5;
      Int result = Module.module(value);
      assertEquals(value,result);
    }
    ”
  And the at least one of the tests fails
  And the user sets the challenge score on 5
  When the user submits the challenge
  Then the system creates a new id for the challenge
  And saves the challenge




Scenario: Admin user wants to create a new invalid test based challenge
  Given the user “root” is already logged on
  And the user is a content creator user
  And the text of the challenge is
  "
    Public class ParImpar {
      public boolean isPar (int x) {
          return x % 2 == 0 ;
      }
    }
  "
  And the text of the tests is:
  “
    @Test
    Public void test1(){
      Int value= 4;
      Int result= ParImpar.isPar(value);
      assertTrue(result);
    }
    @Test
    Public void test2(){
      Int value= 7;
      Int result= ParImpar.isPar(value);
      assertFalse(result);
    }
  “
  And all tests pass
  When the user submits the challenge
  Then the system should not save the challenge


Scenario: Admin user wants to create a challenge that already has been submitted
  Given the user “JohnyMeLavo” is already logged on
  And he has admin permission
  And the challenge is already charged in the system
  When he submits the challenge
  Then the system shouldn’t charge the challenge
  And inform the user that his challenge is equal another challenge that already has been charged



Scenario: Admin user wants to create a new incomplete test based challenge
  Given  the user “Roberta” is already logged on
  And the user is a content creator user
  And the text of the challenge is:
    “
    Public class Max{
      Public static int maxi(int n, int m){
        if(n>=m){
          Return n;
        }
        Else{
          Return m;
        }
      }
    }
  “
  And the text of the tests is: “”
  When the user submits the challenge
  Then the system should not save the challenge

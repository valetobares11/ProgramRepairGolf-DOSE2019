#Authors: Borda, Celi, de Prada, Goldenberg.

Scenario: Admin user wants to create a new valid test based challenge
  Given the user “root” is already logged on
  And the user has admin permission
  And the text of the challenge is
  ´´´
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
  ´´´
  And the text of the tests is:
  ´´´
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
  ´´´
  And the at least one of the tests fails
  And the user sets the challenge score on 5
  When the user submits the challenge
  Then the system creates a new id for the challenge
  And saves the challenge

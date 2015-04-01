/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajuda;

import static java.lang.Thread.sleep;
import java.util.Random;

public class Ajuda {

    public Ajuda() {
    }

    public int random_entre(int LimiteInferior, int LimiteSuperior) {
        if (LimiteInferior < LimiteSuperior) {
            return new Random().nextInt(LimiteSuperior - LimiteInferior) + LimiteInferior + 1;
        } else {
            return new Random().nextInt(LimiteInferior - LimiteSuperior) + LimiteSuperior + 1;
        }
    }

    public void sleep_entre(int LimiteInferior, int LimiteSuperior) {
        try {
            sleep(random_entre(LimiteInferior, LimiteSuperior));
        } catch (InterruptedException ex) {
        }
    }
}

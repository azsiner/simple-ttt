package com.siner.ttt.game;

/**
 * Type of marks for a given square
 *
 * @author asiner
 */

public enum MarkType
{
    X_MARK {
        @Override
        public String toString(){
            return "X";
        }
    },
     O_MARK {
         @Override
        public String toString(){
             return "O";
         }
     },
     NO_MARK {
         @Override
        public String toString(){
             return "-";
         }
     };
}

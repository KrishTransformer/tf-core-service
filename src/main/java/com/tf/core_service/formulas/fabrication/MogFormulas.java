package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Mog;


public class MogFormulas {
    public static Mog calculateMog(Integer kVA, Integer mog_Tlt_Ang,boolean mogBoolUser) {
        Mog mog = new Mog();
        mog.setMog_Tlt_Ang(mog_Tlt_Ang);


        //Todo: User must have an option to choose MOG irrespective to the KVA.
        mog.setMog(mogBoolUser);

        if (kVA <= 2500) {
            mog.setMog_Model( "MOG_SO4");
        } else {
            mog.setMog_Model( "MOG_SO6");
        }

        //Todo: The pages given by NB sir has these two MOGs as well.
        ///The problem is that the mounting angles are different. There are 3 mounting angles
        ///viz. 0deg, 15deg and 30deg. The mounting pad (holder) has to be constructed accordingly.

        if (mog.getMog_Model().equals("MOG_SO6")) {
            mog.setMog_Size(150);
            mog.setMog_Flg_Pipe_OD(168.3);
            mog.setMog_Flg_Pipe_ID(150);
            if (mog.getMog_Tlt_Ang() <= 0) {
                mog.setMog_Flg_Pipe_L(25);
            } else {
                mog.setMog_Flg_Pipe_L(150);
            }
            mog.setMog_Flg_OD(234); //TODO: in the data given by NB the OD is 235.
            mog.setMog_Flg_ID(mog.getMog_Flg_Pipe_ID());
            mog.setMog_Flg_Thick(12);
            mog.setMog_Flg_PCD(209.7);
            mog.setMog_Flg_Bolt_Dia(13);
            mog.setMog_Flg_Bolt_L(31);
            mog.setMog_Flg_Bolt_Count(8);

        } else {
            mog.setMog_Size(100);
            mog.setMog_Flg_Pipe_OD(141.3);
            mog.setMog_Flg_Pipe_ID(125);
            if (mog.getMog_Tlt_Ang() <= 0) {
                mog.setMog_Flg_Pipe_L(25);
            } else {
                mog.setMog_Flg_Pipe_L(150);
            }
            mog.setMog_Flg_OD(190);
            mog.setMog_Flg_ID(mog.getMog_Flg_Pipe_ID());
            mog.setMog_Flg_Thick(12);
            mog.setMog_Flg_PCD(165.0);
            mog.setMog_Flg_Bolt_Dia(12);
            mog.setMog_Flg_Bolt_L(31);
            mog.setMog_Flg_Bolt_Count(6);

        }
        return mog;
    }

}

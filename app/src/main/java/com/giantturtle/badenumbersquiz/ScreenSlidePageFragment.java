/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.giantturtle.badenumbersquiz;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    CheckBox checkBoxSave;
    TextView eNumberHeading;
    DbHelper db;

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);//ARG_PAGE is used as a key or tag for this integer
        fragment.setArguments(args);


        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // ovde menjam sadržaj svakog novokreiranog fragmenta prilikom swipe
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_prepare_study, container, false);

        // Set the title view to show the page number.
        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                getString(R.string.title_template_step, mPageNumber + 1));

        String[] badENumbersArray = getResources().getStringArray(R.array.badENumbersCardArray);//ovo je naslov, mogu da podešavam na svoj način kao ispod a da ovo obrišem
        TextView nedoumiceContent =((TextView) rootView.findViewById(R.id.contentForShow));
         nedoumiceContent.setText(badENumbersArray[mPageNumber]);// ovde podešavam da prikazuje sadržaj badENumbersArray-a

        String[] headingForBadENumbers = getResources().getStringArray(R.array.headingForBadENumbersArray);
        eNumberHeading = (TextView)rootView.findViewById(R.id.eNumberHeading);
        eNumberHeading.setText(headingForBadENumbers[mPageNumber]);



        /* mogu da stavim još neke komponente ovde da prikazuje kada se menja stranica */

        checkBoxSave = (CheckBox)rootView.findViewById(R.id.checkBoxSaveCard);


               /*checkBoxSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Događaj je rukovan", Toast.LENGTH_LONG).show();
            }
        });*/

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mogu ovde da stavljam listenere jer ovaj metod se pokreće kad je fragment kreiran


            db= new DbHelper(getActivity());
        //TODO write the code that checks if the card was saved in database and sets this checkbox to checked and sets the text on it also
           if (db.checkIfCardWasSaved(mPageNumber)){
               checkBoxSave.setChecked(true);
               checkBoxSave.setText(R.string.checkbox_card_saved);
           }

        checkBoxSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.saveCard(mPageNumber,eNumberHeading.getText().toString());
                checkBoxSave.setText(R.string.checkbox_card_saved);


               // Toast.makeText(getActivity(), "Događaj je rukovan: " + mPageNumber, Toast.LENGTH_SHORT).show();
               // Toast.makeText(getActivity(), "Naslov nedoumice: " + eNumberHeading.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*  2.This following two methods to handle resuming.

public void onAttach(Activity activity) {
    super.onAttach(activity);
    //do something
}

@Override
public void onDetach() {
    super.onDetach();
    //do something
} */

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}

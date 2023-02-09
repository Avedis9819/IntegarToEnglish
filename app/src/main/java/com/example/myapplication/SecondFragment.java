package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private static final String[] ones = {"zero", "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen",
            "nineteen"};

    private static final String[] tens = {"", "", "twenty", "thirty", "forty", "fifty",
            "sixty", "seventy", "eighty", "ninety"};

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView headerValues = view.findViewById(R.id.header);
        headerValues.setTypeface(null, Typeface.BOLD_ITALIC);
        headerValues.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        final EditText inputNumber = view.findViewById(R.id.intInputField);
        final TextView answerField = view.findViewById(R.id.outputField);

        binding.convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputNumber.getText().toString();
                if(input.equals("")) {
                    answerField.setText("Please insert a number.");
                    answerField.setTextColor(Color.RED);
                }
                try {
                    int intInput = Integer.parseInt(input);
                    String answer = convertToText(intInput);
                    answerField.setTextColor(Color.GREEN);
                    answerField.setText(answer);
                    answerField.setTypeface(null, Typeface.BOLD);
                } catch (NumberFormatException e) {
                    answerField.setText("Please insert a valid number");
                    answerField.setTextColor(Color.RED);
                    answerField.setTypeface(null, Typeface.BOLD);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static String convertToText(int number) {
        if(number == 0)
            return ones[0];

        if(number < 0)
            return "Please insert a positive integer";

        if(number > 999_999_999)
            return "Please insert a smaller number";

        if(number < 20)
            return ones[number];

        if(number < 100)
            return tens[number / 10] + ((number % 10 == 0) ? "" : " " + ones[number % 10]);

        if(number < 1000)
            return ones[number / 100] + " hundred" + ((number % 10 == 0) ? "" : " and " + convertToText(number % 100));

        if(number < 1_000_000)
            return convertToText(number / 1000) + " thousand" + ((number % 10 == 0) ? "" : " " + convertToText(number % 1_000));

        return convertToText(number / 1_000_000) + " million" + ((number % 10 == 0) ? "" : " " + convertToText(number % 1_000_000));
    }

}
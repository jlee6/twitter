package com.exercise.twitter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class HandleDialog extends DialogFragment {
    public static final String TAG = HandleDialog.class.getSimpleName();

    private OnHandleChanged callback;

    public void setOnHandleChangedCallback(OnHandleChanged callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.handle_dialog, null);

        final TextInputEditText input = (TextInputEditText) view.findViewById(R.id.et_handle);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Twitter Handle")
                .setView(view)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (input.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Must set handle", Toast.LENGTH_LONG).show();
                            return;
                        }

                        callback.change(input.getText().toString());
                    }
                });

        return builder.create();
    }

    public interface OnHandleChanged {
        void change(String result);
    }
}

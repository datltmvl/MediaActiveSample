package jp.example.app.onidenwa;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhoneRingingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhoneRingingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhoneRingingFragment extends Fragment {
    // Action pick up a call
    public static final int PICK_UP_PHONE = 1;
    // Action cut off a call
    public static final int CUT_OFF_PHONE = 2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int mParam1 = -1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    MediaPlayer mMediaPlayer;
    // button accept call
    private Button mBtnAccept;
    // button denial call
    private Button mBtnDenial;

    public PhoneRingingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PhoneRingingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneRingingFragment newInstance(int param1) {
        PhoneRingingFragment fragment = new PhoneRingingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_phone_ringing, container, false);
        ImageView imageView = root.findViewById(R.id.imageCaller);
        if (mParam1 != -1) {
            imageView.setImageResource(mParam1);
        }
        mBtnAccept = root.findViewById(R.id.btn_accept);
        mBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(PICK_UP_PHONE);
            }
        });
        mBtnDenial = root.findViewById(R.id.btn_denial);
        mBtnDenial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(CUT_OFF_PHONE);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.se01);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int action) {
        if (mListener != null) {
            mListener.onFragmentInteraction(action);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnTerminalCallListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int action);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }
}

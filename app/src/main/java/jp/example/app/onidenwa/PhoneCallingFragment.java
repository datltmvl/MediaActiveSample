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
 * {@link OnTerminalCallListener} interface
 * to handle interaction events.
 * Use the {@link PhoneCallingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhoneCallingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1 = -1;
    private int mParam2 = -1;

    private OnTerminalCallListener mListener;

    MediaPlayer mMediaPlayer;
    // button terminal call
    private Button mBtnTeminal;
    // avatar of caller
    private ImageView mImgCaller;

    public PhoneCallingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhoneRingingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneCallingFragment newInstance(int param1, int param2) {
        PhoneCallingFragment fragment = new PhoneCallingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_phone_calling, container, false);
        // setup button terminal call
        mBtnTeminal = root.findViewById(R.id.btn_terminal);
        mBtnTeminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(null);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        // setup avatar of caller
        mImgCaller = root.findViewById(R.id.imageCaller);
        if (mParam2 != -1) {
            mImgCaller.setImageResource(mParam2);
        }

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mParam1 != -1) {
            mMediaPlayer = MediaPlayer.create(getContext(), mParam1);
            mMediaPlayer.start();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTerminalCall(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTerminalCallListener) {
            mListener = (OnTerminalCallListener) context;
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
    public interface OnTerminalCallListener {
        // TODO: Update argument type and name
        void onTerminalCall(Uri uri);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}

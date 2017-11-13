package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by HuynhHuu on 13-Nov-17.
 */

public class StudentListAdapter extends ArrayAdapter<Student> {

    private Context mContext;
    private int mResId;
    private List<Student> mStudentList;
    private int lastPosition;

    public StudentListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Student> students) {
        super(context, resource, students);
        mContext = context;
        mResId = resource;
        mStudentList = students;

        lastPosition = -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResId,null);

        ImageView avatarImageView  = (ImageView) view.findViewById(R.id.avatarImageView);
        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);

        Student selectedStudent = mStudentList.get(position);
        String fullName = selectedStudent.getLastName() + ", " + selectedStudent.getFirstName();
        nameTextView.setText(fullName);

        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open("phuchuynh" + ".jpg");
            Drawable drawable = Drawable.createFromStream(is, fullName);
            avatarImageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition)? R.anim.up_from_bottom_fade_in_listview : R.anim.down_from_top_animation);
        view.startAnimation(animation);
        lastPosition = position;

        return view;
    }
}

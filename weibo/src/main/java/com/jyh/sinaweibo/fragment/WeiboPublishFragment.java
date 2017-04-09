package com.jyh.sinaweibo.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.emoji.EmojiKeyboardFragment;
import com.jyh.sinaweibo.emoji.Emojicon;
import com.jyh.sinaweibo.emoji.InputHelper;
import com.jyh.sinaweibo.emoji.OnEmojiClickListener;
import com.jyh.sinaweibo.fragment.base.BaseFragment;
import com.jyh.sinaweibo.util.WeiboPublishContract;
import com.jyh.sinaweibo.widget.WeiboPicturesPreviewer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeiboPublishFragment extends BaseFragment implements View.OnClickListener, WeiboPublishContract.View {
    public static final int MAX_TEXT_LENGTH = 160;
    private static final int SELECT_FRIENDS_REQUEST_CODE = 100;
    private static final String TEXT_TAG = "#输入话题#";
    //编辑内容的输入框
    @BindView(R.id.edit_content)
    EditText mEditContent;

    @BindView(R.id.recycler_images)
    WeiboPicturesPreviewer mLayImages;

    @BindView(R.id.txt_indicator)
    TextView mIndicator;

    @BindView(R.id.icon_back)
    View mIconBack;

    @BindView(R.id.icon_send)
    View mIconSend;

    private WeiboPublishContract.Operator mOperator;
    private final EmojiKeyboardFragment mEmojiKeyboard = new EmojiKeyboardFragment();

    public WeiboPublishFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        this.mOperator = (WeiboPublishContract.Operator) context;
        this.mOperator.setDataView(this);
        super.onAttach(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weibo_publish;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);


        // EmojiKeyboardFragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.lay_emoji_keyboard, mEmojiKeyboard)
                .commit();
        mEmojiKeyboard.setOnEmojiClickListener(new OnEmojiClickListener() {
            @Override
            public void onEmojiClick(Emojicon v) {
                Log.e("WeiboPublishFragment", "InputHelper.input2OSC");
                InputHelper.input2OSC(mEditContent, v);
            }

            @Override
            public void onDeleteButtonClick(View v) {

                InputHelper.backspace(mEditContent);
            }
        });

        // set hide action
        mLayImages.setOnTouchListener(new View.OnTouchListener()

        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEmojiKeyboard.hideAllKeyBoard();
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        mOperator.loadXmlData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mEmojiKeyboard.hideSoftKeyboard();
    }

    @OnClick({R.id.iv_picture, R.id.iv_mention, R.id.iv_tag,
            R.id.iv_emoji, R.id.txt_indicator, R.id.icon_back, R.id.icon_send})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture:
                mLayImages.onLoadMoreClick();
                break;
            case R.id.iv_mention:
                toSelectFriends();
                break;
            case R.id.iv_tag:
                insertTrendSoftware();
                break;
            case R.id.iv_emoji:
                handleEmojiClick(v);
                break;
            case R.id.txt_indicator:
                handleClearContentClick();
                break;
            case R.id.icon_back:
                mOperator.onBack();
                break;
            case R.id.icon_send:
                mOperator.publish();
                break;
        }
    }

    private void handleClearContentClick() {
        mIndicator.setSelected(!mIndicator.isSelected());
        if (mIndicator.isSelected()) {
            mIndicator.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIndicator.setSelected(false);
                }
            }, 1000);
        } else {
            mEditContent.setText("");
        }
    }

    /**
     * Emoji 表情点击
     *
     * @param v View
     */
    private void handleEmojiClick(View v) {
        if (!mEmojiKeyboard.isShow()) {
            mEmojiKeyboard.hideSoftKeyboard();
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEmojiKeyboard.showEmojiKeyBoard();
                }
            }, 280);
        } else {
            mEmojiKeyboard.hideEmojiKeyBoard();
        }
    }

    /**
     * 插入 #软件名#
     */
    private void insertTrendSoftware() {
        final EditText editText = mEditContent;
        final int maxTextLen = MAX_TEXT_LENGTH;
        int curTextLength = editText.getText().length();
        if (curTextLength >= maxTextLen)
            return;
        String software = TEXT_TAG;
        int start, end;
        if ((maxTextLen - curTextLength) >= software.length()) {
            start = editText.getSelectionStart() + 1;
            end = start + software.length() - 2;
        } else {
            int num = maxTextLen - curTextLength;
            if (num < software.length()) {
                software = software.substring(0, num);
            }
            start = editText.getSelectionStart() + 1;
            end = start + software.length() - 1;
        }
        if (start > maxTextLen || end > maxTextLen) {
            start = maxTextLen;
            end = maxTextLen;
        }
        editText.getText().insert(editText.getSelectionStart(), software);
        editText.setSelection(start, end);
    }

    /**
     * @
     */
    private void toSelectFriends() {

        mEditContent.getText().insert(mEditContent.getSelectionStart(), "@");
    }


    @Override
    public String getContent() {
        return mEditContent.getText().toString();
    }

    @Override
    public void setContent(String content) {
        mEditContent.setText(content);
    }

    @Override
    public String[] getImages() {
        return mLayImages.getPaths();
    }


    @Override
    public void setImages(String[] paths) {
        mLayImages.set(paths);
    }

    @Override
    public void onDestroyView() {
        mLayImages.destroy();
        super.onDestroyView();
    }
}

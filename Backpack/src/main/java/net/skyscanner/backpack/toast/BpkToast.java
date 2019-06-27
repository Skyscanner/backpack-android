package net.skyscanner.backpack.toast;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import net.skyscanner.backpack.text.BpkText;

public final class BpkToast {

  public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
  public static final int LENGTH_LONG = Toast.LENGTH_LONG;

  private BpkToast() {
  }

  @NonNull
  public static Toast makeText(Context context, @StringRes int text, int duration) {
    return makeText(context, context.getResources().getText(text), duration);
  }

  @NonNull
  public static Toast makeText(Context context, @Nullable CharSequence text, int duration) {
    final BpkText.FontDefinition font = getToastFont(context);
    return Toast.makeText(context, wrapText(font, text), duration);
  }

  @NonNull
  private static BpkText.FontDefinition getToastFont(@NonNull Context context) {
    return BpkText.getFont(context, BpkText.SM, BpkText.Weight.NORMAL);
  }

  @Nullable
  private static CharSequence wrapText(@NonNull BpkText.FontDefinition font, @Nullable CharSequence text) {
    if (text == null) {
      return null;
    }
    return new SpannableStringBuilder()
      .append(text, new BpkTextSpan(font), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
  }

  private static class BpkTextSpan extends CharacterStyle {

    private final BpkText.FontDefinition mFont;

    private BpkTextSpan(@NonNull BpkText.FontDefinition font) {
      mFont = font;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
      mFont.applyTo(tp);
    }
  }

}

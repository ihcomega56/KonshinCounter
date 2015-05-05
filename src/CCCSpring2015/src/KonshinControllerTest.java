package CCCSpring2015.src;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class KonshinControllerTest {

    @Test
    public void TestIsInvalid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        KonshinController kc = new KonshinController();
        Method m = KonshinController.class.getDeclaredMethod("isInvalid", String.class);
        m.setAccessible(true);

        // 半角数値しか入力させない
        assertThat(m.invoke(kc, ""), is(true));
        assertThat(m.invoke(kc, "あいう"), is(true));
        assertThat(m.invoke(kc, "abc"), is(true));
        assertThat(m.invoke(kc, "１２３"), is(true));
        assertThat(m.invoke(kc, "1２3"), is(true));
        assertThat(m.invoke(kc, "123"), is(false));

    }

    @Test
    public void TestJudge() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        KonshinController kc = new KonshinController();
        Method m = KonshinController.class.getDeclaredMethod("judge", int.class, double.class, double.class, double.class);
        m.setAccessible(true);

        // 空のテキストボックスを許容しない
        String expectedInputCaution = "全部入力してー٩(๑`ȏ´๑)۶";
        assertThat(m.invoke(kc, 1, 0, 1, 1), is(expectedInputCaution));
        assertThat(m.invoke(kc, 1, 1, 0, 1), is(expectedInputCaution));
        assertThat(m.invoke(kc, 1, 1, 1, 0), is(expectedInputCaution));
        assertThat(m.invoke(kc, 1, 1, 1, 1), not(expectedInputCaution));

        // 参加人数を0未満にしない
        String expectedMinusCaution = "もう減らさんでー٩(๑`ȏ´๑)۶";
        assertThat(m.invoke(kc, -1, 1, 1, 1), is(expectedMinusCaution));
        assertThat(m.invoke(kc, 0, 1, 1, 1), not(expectedMinusCaution));

        // 参加率の判定を正しく行う
        String expectedAttendOkMessage = "大体予想通り♡";
        String expectedAttendNgMessage = "調整が必要＞＜";

        assertThat((String)m.invoke(kc, 9001, 10000, 1, 1), containsString(expectedAttendOkMessage));
        assertThat((String)m.invoke(kc, 10999, 10000, 1, 1), containsString(expectedAttendOkMessage));
        assertThat((String)m.invoke(kc, 9000, 10000, 1, 1), containsString(expectedAttendNgMessage));
        assertThat((String)m.invoke(kc, 11000, 10000, 1, 1), containsString(expectedAttendNgMessage));

        // 費用回収率の判定を正しく行う
        String expectedPayOkMessage = "収支OKはっぴー";
        String expectedFeeNgMessage = "お金足りん感じ";
        String expectedFoodNgMessage = "料理足りんかも";

        assertThat((String)m.invoke(kc, 9001, 1, 10000, 1), containsString(expectedPayOkMessage));
        assertThat((String)m.invoke(kc, 10999, 1, 10000, 1), containsString(expectedPayOkMessage));
        assertThat((String)m.invoke(kc, 9000, 1, 10000, 1), containsString(expectedFeeNgMessage));
        assertThat((String)m.invoke(kc, 11000, 1, 10000, 1), containsString(expectedFoodNgMessage));

    }
}
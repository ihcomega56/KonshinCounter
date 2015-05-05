package CCCSpring2015.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ihcomega on 15/04/19.
 */
public class KonshinController {

    /**
     * 計算結果表示ラベル
     */
    @FXML
    private Label resultLabel;

    /**
     * 予約人数入力フィールド
     */
    @FXML
    private TextField reservedNumber;

    /**
     * 支払い済み金額入力フィールド
     */
    @FXML
    private TextField paidAmount;

    /**
     * 1人あたりの会費入力フィールド
     */
    @FXML
    private TextField membershipFee;

    /**
     * 人数を1人追加するボタン
     */
    @FXML
    private Button plusButton;

    /**
     * 人数を1人削減するボタン
     */
    @FXML
    private Button minusButton;

    /**
     * 参加確定人数
     */
    private int checkedInNumber;

    /**
     * チェックイン済み人数を１人追加するメソッド
     *
     * @param event 1人++ボタン押下時に発生するイベント
     */
    @FXML
    private void plus(ActionEvent event) {
        refreshParts(n -> n + 1);
    }

    /**
     * チェックイン済み人数を１人削減するメソッド
     *
     * @param event 1人--ボタン押下時に発生するイベント
     */
    @FXML
    private void minus(ActionEvent event) {
        refreshParts(n -> n - 1);
    }

    /**
     * 入力値をチェックするメソッド
     *
     * @param inputText 入力値
     * @return 数値以外または空文字のときtrue
     */
    private boolean isInvalid(String inputText) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(inputText);
            return (inputText.isEmpty() || !m.matches());
    }

    /**
     * ボタン・ラベルの表示切り替えを行うメソッド
     *
     * @param operator 人数を増減するための関数型インタフェース
     */
    private void refreshParts(UnaryOperator<Integer> operator) {
        String reservedNumber = this.reservedNumber.getText();
        String paidAmount = this.paidAmount.getText();
        String membershipFee = this.membershipFee.getText();
        String msg;

        if (isInvalid(reservedNumber) || isInvalid(paidAmount) || isInvalid(membershipFee)) {
            msg = "半角数字いれて！空欄ダメ";
        } else {
            checkedInNumber = operator.apply(checkedInNumber);
            msg = judge(checkedInNumber,
                    Integer.parseInt(reservedNumber),
                    Integer.parseInt(paidAmount),
                    Integer.parseInt(membershipFee));
        }
        resultLabel.setText(msg);
    }

    /**
     * 参加率・費用回収率を計算するメソッド
     *
     * @param checkedInNumber 実際の参加人数
     * @param reservedNumber  事前に参加意志表明した人数
     * @param paidAmount      予約時に支払った金額
     * @param membershipFee   1人あたりの会費
     * @return 計算結果に基づく表示用メッセージ
     */
    private String judge(int checkedInNumber, double reservedNumber, double paidAmount, double membershipFee) {
        StringBuilder msg = new StringBuilder();

        if (reservedNumber == 0 || paidAmount == 0 || membershipFee == 0) {
            msg.append("全部入力してー٩(๑`ȏ´๑)۶");
            return msg.toString();
        }

        if (checkedInNumber < 0) {
            // 人数マイナスにされたら0から再スタートする
            this.checkedInNumber = 0;
            msg.append("もう減らさんでー٩(๑`ȏ´๑)۶");
            return msg.toString();
        }


        // チェックイン済み人数
        msg.append("今" + checkedInNumber + "人(๑ˇεˇ๑)•*¨*•.¸¸♪");

        double attendRate = checkedInNumber / reservedNumber * 100;
        double paidRate = membershipFee * checkedInNumber / paidAmount * 100;

        // 参加率
        msg.append("\n参加率" + String.format("%.2f", attendRate) + "%！");
        msg.append((90 < attendRate && attendRate < 110) ? "大体予想通り♡" : "調整が必要＞＜");

        // 費用回収率
        msg.append("\n回収率" + String.format("%.2f", paidRate) + "%！");
        if (90 < paidRate && paidRate < 110) {
            msg.append("収支OKはっぴー");
        } else if (paidRate <= 90) {
            msg.append("お金足りん感じ");
        } else {
            msg.append("料理足りんかも");
        }
        return msg.toString();
    }

}

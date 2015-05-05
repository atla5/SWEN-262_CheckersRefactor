package GUI;
import GUI.Secondscreen.BtnSSOk;
import GUI.Firstscreen.BtnFSOk;
import GUI.Firstscreen.BtnFCancel;
import GUI.Secondscreen.BtnSCancel;

/**
 * Created by Aaron on 5/2/2015.
 */
interface Mediator {

    void SSOk();
    void FSOk();
    void FCancel();
    void SCancel();
    void registerSSOk(BtnSSOk ssOk);
    void registerFSOk(BtnFSOk ssOk);
    void registerFCancel(BtnFCancel cancel);
    void registerSCancel(BtnSCancel cancel);
    void makeLocalMove();
    void pressDraw();
    void endInQuit();
    void showEndGame(String s);
    int whosTurn();

}


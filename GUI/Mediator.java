package GUI;
import GUI.Secondscreen.BtnSSOk;
import GUI.Firstscreen.BtnFSOk;
/**
 * Created by Aaron on 5/2/2015.
 */
interface Mediator {

    void SSOk();
    void FSOk();
    void registerSSOk(BtnSSOk ssOk);
    void registerFSOk(BtnFSOk ssOk);
}


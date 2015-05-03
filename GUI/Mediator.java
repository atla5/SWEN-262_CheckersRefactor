package GUI;
import GUI.Secondscreen.BtnSSOk;
import GUI.Firstscreen.BtnFSOk;
/**
 * Created by Aaron on 5/2/2015.
 */
interface Mediator {

    void SSOk(Secondscreen second);
    void FSOk(Firstscreen first, Secondscreen second);
    void registerSSOk(BtnSSOk ssOk);
    void registerFSOk(BtnFSOk ssOk);
}


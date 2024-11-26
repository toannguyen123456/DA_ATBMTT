package controller;
import view.DigitalSignatureView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class DigitalSignatureController implements ActionListener {
    private DigitalSignatureView view;
    public DigitalSignatureController(DigitalSignatureView view) {
        this.view = view;
    }
    /**
     * Xử lý các sự kiện khi người dùng tương tác với giao diện.
     * @param e sự kiện  chứa thông tin về sự kiện đã xảy ra.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        Object source = e.getSource();
        switch (src){
            case "Áp dụng cấu hình":
                if (source instanceof JButton) {
                    try{
                       this.view.config();
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Ký số":
                if (source instanceof JButton) {
                    try{
                        this.view.kySo();
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Xác minh":
                if (source instanceof JButton) {
                    try{
                        this.view.CheckResult();
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }

                break;
            default:
                break;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pengolahancitra;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 *
 * @author Mas Bud
 */
public class ProsesPengolahan {

    public ImageIcon SumberIkon;
    public ImageIcon SkalaIkon;
    public ImageIcon IkonHasilSkala;

    public Image SumberGambar;
    public Image SkalaGambar;
    public Image GambarHasilSkala;
    public Image HasilGambar;

    public String URLGambar;
    public boolean ScaledFlag = false;
    public BufferedImage SourceBuffer;
    public BufferedImage ResultBuffer;
    public long sTinggi;
    public long sLebar;

    ProsesPengolahan(String Url, long width, long height) {
        URLGambar = Url;
        if (width <= 0 || height <= 0) {
            ScaledFlag = false;
        } else {
            ScaledFlag = true;
            sLebar = width;
            sTinggi = height;
        }
    }

    public ImageIcon GetIcon() {
        if (!URLGambar.equals("")) {

            SumberIkon = new ImageIcon(URLGambar);
            SumberGambar = SumberIkon.getImage();

            try {
                SourceBuffer = ImageIO.read(new File(URLGambar));
            } catch (IOException x) {
                JOptionPane.showMessageDialog(null, x.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            System.out.println("panjang = " + SumberIkon.getIconWidth());
            System.out.println("tinggi = " + SumberIkon.getIconHeight());

            if (ScaledFlag) {
                SkalaGambar = SumberGambar.getScaledInstance((int) sLebar, (int) sTinggi, Image.SCALE_SMOOTH);
                SkalaIkon = new ImageIcon(SkalaGambar);
                return SkalaIkon;
            } else {
                return SumberIkon;
            }
        } else {
            return null;
        }
    }

    public void GrayScale() {
        //proses merubah gambar RGB menjadi Grayscale
        ResultBuffer = deepCopy(SourceBuffer); //mengambil data dari gambar asli

        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();

        long x, y;
        int RGB, Red, Green, Blue, Gray;
        Color tWarna;

        //rumus untuk merubah dari RGB ke grayscale
        for (x = 0; x < tWidth; x++) {
            for (y = 0; y < tHeight; y++) {
                RGB = ResultBuffer.getRGB((int) x, (int) y);
                tWarna = new Color(RGB);
                System.out.println("(" + x + "," + y + ")");
                Red = tWarna.getRed();
                System.out.println("Red = " + Red);
                Green = tWarna.getGreen();
                System.out.println("Green = " + Green);
                Blue = tWarna.getBlue();
                System.out.println("Blue = " + Blue);
                Gray = (Red + Green + Blue) / 3;
                System.out.println("Gray = " + Gray);

                tWarna = new Color(Gray, Gray, Gray);
                ResultBuffer.setRGB((int) x, (int) y, tWarna.getRGB());
            }
        }

        HasilGambar = (Image) ResultBuffer;
        GambarHasilSkala = HasilGambar.getScaledInstance((int) sLebar, (int) sTinggi, Image.SCALE_SMOOTH);
        IkonHasilSkala = new ImageIcon(GambarHasilSkala);
    }

    public void Biner() {
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        long x, y;
        int RGB, Red, Green, Blue, Gray;
        Color tWarna;

        for (x = 0; x < tWidth; x++) {
            for (y = 0; y < tHeight; y++) {
                RGB = ResultBuffer.getRGB((int) x, (int) y);
                tWarna = new Color(RGB);
                System.out.println("(" + x + "," + y + ")");
                Red = tWarna.getRed();
                System.out.println("Red = " + Red);
                Green = tWarna.getGreen();
                System.out.println("Green = " + Green);
                Blue = tWarna.getBlue();
                System.out.println("Blue = " + Blue);
                Gray = (Red + Green + Blue) / 3;
                System.out.println("Gray = " + Gray);

                if (Gray <= 128) {
                    Gray = 0;
                } else {
                    Gray = 255;
                }

                tWarna = new Color(Gray, Gray, Gray);
                ResultBuffer.setRGB((int) x, (int) y, tWarna.getRGB());
            }
        }
        HasilGambar = (Image) ResultBuffer;
        GambarHasilSkala = HasilGambar.getScaledInstance((int) sLebar, (int) sTinggi, Image.SCALE_SMOOTH);
        IkonHasilSkala = new ImageIcon(GambarHasilSkala);
    }

    public void BackColor() {
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        long x, y;
        int RGB, Red, Green, Blue, Gray;
        Color tWarna;

        for (x = 0; x < tWidth; x++) {
            for (y = 0; y < tHeight; y++) {
                RGB = ResultBuffer.getRGB((int) x, (int) y);
                tWarna = new Color(RGB);
                Red = tWarna.getRed();
                Green = tWarna.getGreen();
                Blue = tWarna.getBlue();
                tWarna = new Color(Red, Green, Blue);
                ResultBuffer.setRGB((int) x, (int) y, tWarna.getRGB());
            }
        }

        HasilGambar = (Image) ResultBuffer;
        GambarHasilSkala = HasilGambar.getScaledInstance((int) sLebar, (int) sTinggi, Image.SCALE_SMOOTH);
        IkonHasilSkala = new ImageIcon(GambarHasilSkala);
    }

    public byte[] GetPikselData() {
        byte[] temp = new byte[SourceBuffer.getWidth() * SourceBuffer.getHeight() * 3];
        int i, j, k, RGB;
        Color tWarna;

        k = 0;
        for (i = 0; i < SourceBuffer.getWidth(); i++) {
            for (j = 0; j < SourceBuffer.getHeight(); j++) {
                RGB = SourceBuffer.getRGB(i, j);
                tWarna = new Color(RGB);

                temp[k] = (byte) tWarna.getRed();
                k++;
                temp[k] = (byte) tWarna.getGreen();
                k++;
                temp[k] = (byte) tWarna.getBlue();
                k++;
            }
        }
        return temp;
    }

    public void ResultFromData(byte[] tData) {
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getHeight();
        long tHeight = ResultBuffer.getHeight();
        long x, y;
        int k, RGB, Red, Green, Blue;
        Color tWarna;

        k = 0;
        for (x = 0; x < tWidth; x++) {
            for (y = 0; y < tHeight; y++) {
                Red = tData[k] & (0xff);
                k++;
                Green = tData[k] & (0xff);
                k++;
                Blue = tData[k] & (0xff);
                k++;

                tWarna = new Color(Red, Green, Blue);
                ResultBuffer.setRGB((int) x, (int) y, tWarna.getRGB());
            }
        }

        HasilGambar = (Image) ResultBuffer;
        GambarHasilSkala = HasilGambar.getScaledInstance((int) sLebar, (int) sTinggi, Image.SCALE_SMOOTH);
        IkonHasilSkala = new ImageIcon(GambarHasilSkala);
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

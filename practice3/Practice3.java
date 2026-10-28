package practice3;

import java.time.Year;

enum HangXe {
    HONDA, YAMAHA, TOYOTA, SUZUKI
}

class ChuXe {
    private String cmnd;
    private String hoTen;
    private String email;

    public ChuXe(String cmnd, String hoTen, String email) {
        if (!cmnd.matches("[0-9]{12}")) {
            throw new IllegalArgumentException("CMND phai co dung 12 chu so");
        }

        if (!email.matches("[A-Za-z0-9_+.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}")) {
            throw new IllegalArgumentException("Email khong dung dinh dang");
        }
        this.cmnd = cmnd;
        this.hoTen = hoTen;
        this.email = email;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void hienThi() {
        System.out.println("  Chu xe: " + hoTen + ", CMND: " + cmnd + ", email: " + email);
    }
}

abstract class PhuongTien {
    protected String soXe;
    protected HangXe hang;
    protected int namSanXuat;
    protected String mau;
    protected ChuXe chuXe;

    public PhuongTien(String soXe, HangXe hang, int namSanXuat, String mau, ChuXe chuXe) {
        if (soXe.length() != 5) {
            throw new IllegalArgumentException("So xe phai co dung 5 ky tu");
        }
        if (hang == null) {
            throw new IllegalArgumentException("Hang xe phai la HONDA, YAMAHA, TOYOTA hoac SUZUKI");
        }
        if (namSanXuat <= 2000 || namSanXuat > Year.now().getValue()) {
            throw new IllegalArgumentException("Nam san xuat phai > 2000 va <= nam hien tai");
        }
        this.soXe = soXe;
        this.hang = hang;
        this.namSanXuat = namSanXuat;
        this.mau = mau;
        this.chuXe = chuXe;
    }

    public void hienThi() {
        System.out.println("So xe: " + soXe + ", hang: " + hang
                + ", nam: " + namSanXuat + ", mau: " + mau);
        chuXe.hienThi();
    }
}

class OTo extends PhuongTien {
    private int soCho;
    private String kieuDongCo;

    public OTo(String soXe, HangXe hang, int nam, String mau, ChuXe chu, int soCho, String kieu) {
        super(soXe, hang, nam, mau, chu);
        this.soCho = soCho;
        this.kieuDongCo = kieu;
    }

    @Override
    public void hienThi() {
        super.hienThi();
        System.out.println("  O to: " + soCho + " cho, dong co: " + kieuDongCo);
    }
}

class XeMay extends PhuongTien {
    private double congSuat;

    public XeMay(String soXe, HangXe hang, int nam, String mau, ChuXe chu, double congSuat) {
        super(soXe, hang, nam, mau, chu);
        this.congSuat = congSuat;
    }

    @Override
    public void hienThi() {
        super.hienThi();
        System.out.println("  Xe may: cong suat " + congSuat + " KW");
    }
}

class XeTai extends PhuongTien {
    private double trongTai;

    public XeTai(String soXe, HangXe hang, int nam, String mau, ChuXe chu, double trongTai) {
        super(soXe, hang, nam, mau, chu);
        this.trongTai = trongTai;
    }

    @Override
    public void hienThi() {
        super.hienThi();
        System.out.println("  Xe tai: trong tai " + trongTai + " tan");
    }
}

class QuanLyXe {
    private PhuongTien[] ds = new PhuongTien[100];
    private int n = 0;

    public PhuongTien timSoXe(String soXe) {
        for (int i = 0; i < n; i++) {
            if (ds[i].soXe.equalsIgnoreCase(soXe)) {
                return ds[i];
            }
        }
        return null;
    }

    public ChuXe timChu(String cmnd) {
        for (int i = 0; i < n; i++) {
            if (ds[i].chuXe.getCmnd().equals(cmnd)) {
                return ds[i].chuXe;
            }
        }
        return null;
    }

    public void them(PhuongTien xe) {
        if (timSoXe(xe.soXe) != null) {
            throw new IllegalArgumentException("So xe da ton tai");
        }
        ChuXe chuCu = timChu(xe.chuXe.getCmnd());

        if (chuCu != null && chuCu != xe.chuXe) {
            throw new IllegalArgumentException("CMND da thuoc ve mot chu xe khac");
        }
        ds[n] = xe;
        n++;
    }

    public void hienThi() {
        for (int i = 0; i < n; i++) {
            ds[i].hienThi();
        }
    }

    public void timTheoCmnd(String cmnd) {
        boolean timThay = false;
        for (int i = 0; i < n; i++) {
            if (ds[i].chuXe.getCmnd().equals(cmnd)) {
                ds[i].hienThi();
                timThay = true;
            }
        }
        if (!timThay) {
            System.out.println("Khong tim thay xe");
        }
    }

    public void xoaTheoHang(HangXe hang) {
        int count = 0;
        int i = 0;
        while (i < n) {
            if (ds[i].hang == hang) {
                for (int j = i; j < n - 1; j++) {
                    ds[j] = ds[j + 1];
                }
                n--;
                ds[n] = null;
                count++;
                // Chua tang i vi xe tiep theo vua duoc don ve vi tri i.
            } else {
                i++;
            }
        }
        System.out.println("Da xoa " + count + " xe");
    }

    public void hangNhieuNhat() {
        if (n == 0) {
            System.out.println("Danh sach rong");
            return;
        }
        HangXe[] cacHang = HangXe.values();
        int[] dem = new int[cacHang.length];
        int max = 0;
        for (int i = 0; i < cacHang.length; i++) {
            for (int j = 0; j < n; j++) {
                if (ds[j].hang == cacHang[i]) {
                    dem[i]++;
                }
            }
            if (dem[i] > max) {
                max = dem[i];
            }
        }
        for (int i = 0; i < cacHang.length; i++) {
            if (dem[i] == max) {
                System.out.println(cacHang[i] + ": " + max + " xe (nhieu nhat)");
            }
        }
    }

    public void sapXep() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (ds[i].soXe.compareTo(ds[j].soXe) < 0) {
                    PhuongTien tam = ds[i];
                    ds[i] = ds[j];
                    ds[j] = tam;
                }
            }
        }
        hienThi();
    }

    public void thongKe() {
        int oTo = 0, xeMay = 0, xeTai = 0;
        for (int i = 0; i < n; i++) {
            if (ds[i] instanceof OTo) {
                oTo++;
            } else if (ds[i] instanceof XeMay) {
                xeMay++;
            } else if (ds[i] instanceof XeTai) {
                xeTai++;
            }
        }
        System.out.println("O to: " + oTo + ", xe may: " + xeMay + ", xe tai: " + xeTai);
    }
}

public class Practice3 {
    public static void main(String[] args) {
        QuanLyXe ql = new QuanLyXe();
        int namHienTai = Year.now().getValue();
        ChuXe chu1 = new ChuXe("000000000001", "Nguyen Van A", "vana@gmail.com");
        ChuXe chu2 = new ChuXe("000000000002", "Tran Xuan Bach", "bach1029@gmail.com");
        ChuXe chu3 = new ChuXe("000000000003", "Phung Thanh Do", "mixi@gmail.com");

        System.out.println("\n1. Them phuong tien");
        ql.them(new OTo("10001", HangXe.HONDA, 2001, "Do", chu1, 5, "Xang"));
        ql.them(new XeMay("30003", HangXe.HONDA, namHienTai, "Den", chu1, 8));
        ql.them(new XeTai("20002", HangXe.TOYOTA, 2020, "Trang", chu2, 5));
        ql.them(new OTo("50005", HangXe.YAMAHA, 2021, "Xanh", chu3, 4, "Xang"));
        ql.them(new XeMay("40004", HangXe.SUZUKI, 2022, "Den", chu3, 7));
        ql.hienThi();

        System.out.println("\n2. Tim theo so xe 20002");
        PhuongTien xe = ql.timSoXe("20002");
        if (xe != null) {
            xe.hienThi();
        } else {
            System.out.println("Khong tim thay xe");
        }

        System.out.println("\n3. Tim xe cua chu co CMND 000000000001");
        ql.timTheoCmnd("000000000001");

        System.out.println("\n4. Xoa tat ca xe hang SUZUKI");
        ql.xoaTheoHang(HangXe.SUZUKI);
        ql.hienThi();

        System.out.println("\n5. Hang co nhieu xe nhat");
        ql.hangNhieuNhat();

        System.out.println("\n6. Sap xep so xe giam dan");
        ql.sapXep(); // 50005, ..., 10001.

        System.out.println("\n7. Thong ke tung loai xe");
        ql.thongKe(); 

        System.out.println("\nKiem tra cac loi");
        try {
            ql.them(new XeMay("10001", HangXe.HONDA, 2020, "Do", chu1, 8));
        } catch (IllegalArgumentException e) {
            System.out.println("Trung so xe: " + e.getMessage());
        }

        try {
            new XeMay("1234", HangXe.HONDA, 2020, "Do", chu1, 8);
        } catch (IllegalArgumentException e) {
            System.out.println("So xe thieu ky tu: " + e.getMessage());
        }

        try {
            new XeMay("123456", HangXe.HONDA, 2020, "Do", chu1, 8);
        } catch (IllegalArgumentException e) {
            System.out.println("So xe thua ky tu: " + e.getMessage());
        }

        try {
            HangXe.valueOf("FORD");
        } catch (IllegalArgumentException e) {
            System.out.println("Hang ngoai danh sach: " + e.getMessage());
        }

        try {
            new XeMay("60006", null, 2020, "Do", chu1, 8);
        } catch (IllegalArgumentException e) {
            System.out.println("Thieu hang xe: " + e.getMessage());
        }

        try {
            new XeMay("60006", HangXe.HONDA, 2000, "Do", chu1, 8);
        } catch (IllegalArgumentException e) {
            System.out.println("Nam <= 2000: " + e.getMessage());
        }

        try {
            new XeMay("60006", HangXe.HONDA, namHienTai + 1, "Do", chu1, 8);
        } catch (IllegalArgumentException e) {
            System.out.println("Nam vuot hien tai: " + e.getMessage());
        }

        try {
            new ChuXe("1234567", "naruto", "abc@gmail.com");
        } catch (IllegalArgumentException e) {
            System.out.println("CMND thieu chu so: " + e.getMessage());
        }

        try {
            new ChuXe("1234567890123", "sasuke", "abc@gmail.com");
        } catch (IllegalArgumentException e) {
            System.out.println("CMND thua chu so: " + e.getMessage());
        }

        try {
            new ChuXe("12345678901A", "sakura", "abc@gmail.com");
        } catch (IllegalArgumentException e) {
            System.out.println("CMND chua chu cai: " + e.getMessage());
        }

        try {
            ChuXe chuTrung = new ChuXe("000000000001", "kakashi", "xyz@gmail.com");
            ql.them(new XeTai("60006", HangXe.TOYOTA, 2020, "Do", chuTrung, 5));
        } catch (IllegalArgumentException e) {
            System.out.println("Trung CMND chu xe: " + e.getMessage());
        }

        try {
            new ChuXe("000000000004", "obito", "abc.com");
        } catch (IllegalArgumentException e) {
            System.out.println("Email sai: " + e.getMessage());
        }
    }
}


package practice2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

abstract class HangHoa {
    protected String ma;
    protected String ten;
    protected int soLuong;
    protected double donGia;

    public HangHoa(String ma, String ten, int soLuong, double donGia) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("So luong phai >= 0");
        }
        this.ma = ma;
        this.ten = ten;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public abstract double getThueSuat();
    public abstract String danhGia();

    public double tienVAT() {
        return soLuong * donGia * getThueSuat();
    }

    public void hienThi() {
        System.out.println("Ma: " + ma + ", ten: " + ten + ", ton: " + soLuong
                + ", don gia: " + donGia + ", VAT: " + tienVAT()
                + ", danh gia: " + danhGia());
    }
}

class ThucPham extends HangHoa {
    private LocalDate ngaySanXuat;
    private LocalDate hanSuDung;
    private String nhaCungCap;

    public ThucPham(String ma, String ten, int soLuong, double donGia,
                    LocalDate ngaySanXuat, LocalDate hanSuDung, String nhaCungCap) {
        super(ma, ten, soLuong, donGia);
        if (hanSuDung.isBefore(ngaySanXuat)) {
            throw new IllegalArgumentException("Han su dung phai >= ngay san xuat");
        }
        this.ngaySanXuat = ngaySanXuat;
        this.hanSuDung = hanSuDung;
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public double getThueSuat() {
        return 0.05;
    }

    @Override
    public String danhGia() {
        if (soLuong > 0 && hanSuDung.isBefore(LocalDate.now())) {
            return "Kho ban";
        }
        return "Not evaluated";
    }

    @Override
    public void hienThi() {
        super.hienThi();
        System.out.println("  Thuc pham: NSX " + ngaySanXuat + ", HSD " + hanSuDung
                + ", nha cung cap: " + nhaCungCap);
    }
}

class DienMay extends HangHoa {
    private int thangBaoHanh;
    private double congSuat;

    public DienMay(String ma, String ten, int soLuong, double donGia,
                   int thangBaoHanh, double congSuat) {
        super(ma, ten, soLuong, donGia);
        if (thangBaoHanh < 0 || congSuat < 0) {
            throw new IllegalArgumentException("Bao hanh va cong suat phai >= 0");
        }
        this.thangBaoHanh = thangBaoHanh;
        this.congSuat = congSuat;
    }

    @Override
    public double getThueSuat() {
        return 0.1;
    }

    @Override
    public String danhGia() {
        if (soLuong < 3) {
            return "Ban duoc";
        }
        return "Not evaluated";
    }

    @Override
    public void hienThi() {
        super.hienThi();
        System.out.println("  Dien may: bao hanh " + thangBaoHanh
                + " thang, cong suat " + congSuat + " KW");
    }
}

class DoSu extends HangHoa {
    private String nhaSanXuat;
    private LocalDate ngayNhap;

    public DoSu(String ma, String ten, int soLuong, double donGia,
                  String nhaSanXuat, LocalDate ngayNhap) {
        super(ma, ten, soLuong, donGia);
        this.nhaSanXuat = nhaSanXuat;
        this.ngayNhap = ngayNhap;
    }

    @Override
    public double getThueSuat() {
        return 0.1;
    }

    @Override
    public String danhGia() {
        long soNgay = ChronoUnit.DAYS.between(ngayNhap, LocalDate.now());
        if (soLuong > 50 && soNgay > 10) {
            return "Ban cham";
        }
        return "Not evaluated";
    }

    @Override
    public void hienThi() {
        super.hienThi();
        System.out.println("  Do su: nha san xuat " + nhaSanXuat + ", ngay nhap " + ngayNhap);
    }
}

class DSHH {
    private HangHoa[] ds = new HangHoa[100];
    private int n = 0;

    public boolean them(HangHoa hang) {
        for (int i = 0; i < n; i++) {
            if (ds[i].ma.equalsIgnoreCase(hang.ma)) {
                System.out.println("Ma hang da ton tai");
                return false;
            }
        }
        if (n == ds.length) {
            System.out.println("Danh sach da day");
            return false;
        }
        ds[n] = hang;
        n++;
        return true;
    }

    public void hienThi() {
        if (n == 0) {
            System.out.println("Danh sach rong");
        }
        for (int i = 0; i < n; i++) {
            ds[i].hienThi();
        }
    }

    public void thongKe() {
        long tonTP = 0, tonDM = 0, tonSS = 0;
        double vatTP = 0, vatDM = 0, vatSS = 0;
        for (int i = 0; i < n; i++) {
            if (ds[i] instanceof ThucPham) {
                tonTP += ds[i].soLuong;
                vatTP += ds[i].tienVAT();
            } else if (ds[i] instanceof DienMay) {
                tonDM += ds[i].soLuong;
                vatDM += ds[i].tienVAT();
            } else {
                tonSS += ds[i].soLuong;
                vatSS += ds[i].tienVAT();
            }
        }
        System.out.println("Thuc pham: ton kho " + tonTP + ", VAT " + vatTP);
        System.out.println("Dien may: ton kho " + tonDM + ", VAT " + vatDM);
        System.out.println("Sanh su: ton kho " + tonSS + ", VAT " + vatSS);
    }
}

public class Practice2 {
    public static void main(String[] args) {
        DSHH danhSach = new DSHH();

        danhSach.them(new ThucPham("TP01", "Sữa Vinamilk", 150, 35000,
                LocalDate.of(2023, 10, 1), LocalDate.of(2024, 4, 1), "Vinamilk"));

        danhSach.them(new DienMay("DM01", "Tivi Sony", 2, 12500000,
                24, 1.5));

        danhSach.them(new DoSu("DS01", "Bộ ấm chén", 55, 450000,
                "Gốm Bát Tràng", LocalDate.now().minusDays(10)));

        System.out.println("============= DANH SÁCH HÀNG HÓA =============");
        danhSach.hienThi();

        System.out.println("\n============= THỐNG KÊ CHI TIẾT =============");
        danhSach.thongKe();
    }
}

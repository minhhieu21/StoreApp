USE [QuanLyBanHang]
GO
/****** Object:  UserDefinedFunction [dbo].[AUTO_MaHD]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[AUTO_MaHD]()
RETURNS VARCHAR(7)
AS
BEGIN
	DECLARE @ID VARCHAR(7)
	IF (SELECT COUNT(MAHDBan) FROM HoaDon) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(MaHDBan, 5)) FROM HoaDon
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'HD0000' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 and @ID <99 THEN 'HD000'+ CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 99 and @ID <999 THEN 'HD00'+ CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 999 and @ID <9999 THEN 'HD0'+ CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9999 THEN 'HD'+ CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END
GO
/****** Object:  UserDefinedFunction [dbo].[AUTO_MaKH]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[AUTO_MaKH]()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(MAKH) FROM KHACHHANG) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(MAKH, 3)) FROM KHACHHANG
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'KH00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 and @ID <99 THEN 'KH0'+ CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 99 THEN 'KH'+ CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END
GO
/****** Object:  UserDefinedFunction [dbo].[AUTO_MaNV]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[AUTO_MaNV]()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(MaNV) FROM NhanVien) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(MaNV, 3)) FROM NhanVien
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'NV00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'NV0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END
GO
/****** Object:  UserDefinedFunction [dbo].[AUTO_MaSP]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[AUTO_MaSP]()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(MASP) FROM SanPham) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(MASP, 3)) FROM SanPham
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'SP00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 and @ID <99 THEN 'SP0'+ CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 99 THEN 'SP'+ CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END
GO
/****** Object:  Table [dbo].[SanPham]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SanPham](
	[MaSP] [varchar](10) NOT NULL,
	[TenSP] [nvarchar](50) NOT NULL,
	[SoLuong] [int] NULL,
	[DonGiaBan] [float] NULL,
	[Anh] [varchar](max) NULL,
	[GhiChu] [nvarchar](50) NULL,
	[MaDM] [int] NULL,
	[Size] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[MaHDBan] [varchar](10) NOT NULL,
	[MaNV] [varchar](10) NOT NULL,
	[NgayBan] [date] NULL,
	[MaKH] [varchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaHDBan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDonChiTiet]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDonChiTiet](
	[MaHDCT] [int] IDENTITY(1,10) NOT NULL,
	[MaHDBan] [varchar](10) NOT NULL,
	[MaSP] [varchar](10) NOT NULL,
	[SoLuong] [int] NULL,
	[GiamGia] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaHDCT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[DoanhThu_View]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[DoanhThu_View] As

select hd.MaHDBan,hd.NgayBan, SUM(((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100)) as TongTien,
SUM(ct.SoLuong) as SPDaBan

from HoaDon hd 
		inner join HoaDonChiTiet ct on ct.MaHDBan = hd.MaHDBan
		inner join SanPham sp on sp.MaSP = ct.MaSP
GROUP BY hd.MaHDBan, hd.NgayBan
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[MaNV] [varchar](10) NOT NULL,
	[TenNV] [nvarchar](50) NOT NULL,
	[GioiTinh] [bit] NULL,
	[DiaChi] [nvarchar](50) NULL,
	[DienThoai] [varchar](20) NULL,
	[NgaySinh] [date] NULL,
	[MatKhau] [varchar](30) NOT NULL,
	[VaiTro] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[test]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[test] As

select hd.MaHDBan,hd.NgayBan,nv.MaNV,nv.TenNV, SUM(((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100)) as TongTien,
SUM(ct.SoLuong) as SPDaBan

from HoaDon hd 
		inner join HoaDonChiTiet ct on ct.MaHDBan = hd.MaHDBan
		inner join SanPham sp on sp.MaSP = ct.MaSP
		inner join NhanVien nv on nv.MaNV = hd.MaNV
GROUP BY hd.MaHDBan, hd.NgayBan,nv.MaNV,nv.TenNV
GO
/****** Object:  Table [dbo].[DanhMucSP]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMucSP](
	[MaDM] [int] IDENTITY(1,1) NOT NULL,
	[TenDanhMuc] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaDM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[MaKH] [varchar](10) NOT NULL,
	[TenKH] [nvarchar](50) NOT NULL,
	[DiaChi] [nvarchar](50) NULL,
	[Sdt] [varchar](20) NULL,
	[Email] [varchar](50) NULL,
	[Thanhvien] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HoaDon] ADD  CONSTRAINT [MaHDBan]  DEFAULT ([dbo].[AUTO_MaHD]()) FOR [MaHDBan]
GO
ALTER TABLE [dbo].[KhachHang] ADD  CONSTRAINT [MaKH]  DEFAULT ([dbo].[AUTO_MaKH]()) FOR [MaKH]
GO
ALTER TABLE [dbo].[NhanVien] ADD  CONSTRAINT [MaNV]  DEFAULT ([dbo].[AUTO_MaNV]()) FOR [MaNV]
GO
ALTER TABLE [dbo].[SanPham] ADD  CONSTRAINT [MaSP]  DEFAULT ([dbo].[AUTO_MaSP]()) FOR [MaSP]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_KhachHang] FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhachHang] ([MaKH])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhachHang]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_NhanVien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_NhanVien]
GO
ALTER TABLE [dbo].[HoaDonChiTiet]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_HoaDon] FOREIGN KEY([MaHDBan])
REFERENCES [dbo].[HoaDon] ([MaHDBan])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[HoaDonChiTiet] CHECK CONSTRAINT [FK_ChiTietHoaDon_HoaDon]
GO
ALTER TABLE [dbo].[HoaDonChiTiet]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_SanPham] FOREIGN KEY([MaSP])
REFERENCES [dbo].[SanPham] ([MaSP])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[HoaDonChiTiet] CHECK CONSTRAINT [FK_ChiTietHoaDon_SanPham]
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK_DanhMuc_SanPham] FOREIGN KEY([MaDM])
REFERENCES [dbo].[DanhMucSP] ([MaDM])
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK_DanhMuc_SanPham]
GO
/****** Object:  StoredProcedure [dbo].[sp_DoanhThuTheoNgay]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_DoanhThuTheoNgay]
(@Ngay date)
AS BEGIN
	select COUNT(DISTINCT MaHDBan) as TongHoaDonDaBan,
				SUM(SPDaban) as SPDaBan,
				 Sum(TongTien) as DoanhThu, 
				 Min(TongTien) as HDThapNhat,
				 Max (TongTien) as HDCaoNhat,
				 ROUND(AVG(TongTien),2) as TrungBinh
				 from DoanhThu_View where NgayBan = @Ngay
END
GO
/****** Object:  StoredProcedure [dbo].[sp_DoanhThuTheoThang]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_DoanhThuTheoThang]
(@Thang int)
AS BEGIN
	select COUNT(DISTINCT MaHDBan) as TongHoaDonDaBan,
				SUM(SPDaban) as SPDaBan,
				 Sum(TongTien) as DoanhThu, 
				 Min(TongTien) as HDThapNhat,
				 Max (TongTien) as HDCaoNhat,
				 ROUND(AVG(TongTien),2) as TrungBinh
				 from DoanhThu_View where MONTH(NgayBan) = @Thang
END
GO
/****** Object:  StoredProcedure [dbo].[sp_HDCT_MaHD]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE  PROCEDURE [dbo].[sp_HDCT_MaHD] 
 @MaHDBan varchar(10)
 AS
	BEGIN
		select ct.MaHDCT,hd.MaHDBan, sp.TenSP, ct.SoLuong, sp.DonGiaBan, ct.GiamGia, ((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100) as ThanhTien from HoaDonChiTiet ct 
		inner join HoaDon hd on ct.MaHDBan = hd.MaHDBan
		inner join SanPham sp on sp.MaSP = ct.MaSP
		where ct.MaHDBan = @MaHDBan
		GROUP BY ct.MaHDCT,hd.MaHDBan, sp.TenSP,ct.SoLuong,sp.DonGiaBan, ct.GiamGia
	END
GO
/****** Object:  StoredProcedure [dbo].[sp_HoaDon]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE  PROCEDURE [dbo].[sp_HoaDon] 
 AS
	BEGIN
		select hd.MaHDBan, MaNV, NgayBan, MaKH, SUM(((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100)) as TongTien from HoaDon hd 
		inner join HoaDonChiTiet ct 
		on ct.MaHDBan = hd.MaHDBan
		inner join SanPham sp 
		on sp.MaSP = ct.MaSP
		GROUP BY hd.MaHDBan,MaNV, NgayBan, MaKH
		ORDER BY NgayBan desc
	END
GO
/****** Object:  StoredProcedure [dbo].[sp_HoaDonTheoNgay]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE  PROCEDURE [dbo].[sp_HoaDonTheoNgay]
@Ngay date
 AS
	BEGIN
		select hd.MaHDBan, MaNV, NgayBan, MaKH, SUM(((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100)) as TongTien from HoaDon hd 
		inner join HoaDonChiTiet ct 
		on ct.MaHDBan = hd.MaHDBan
		inner join SanPham sp 
		on sp.MaSP = ct.MaSP
		where hd.NgayBan = @Ngay
		GROUP BY hd.MaHDBan,MaNV, NgayBan, MaKH
		ORDER BY NgayBan desc
	END
GO
/****** Object:  StoredProcedure [dbo].[sp_KhBanDuocNgay]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_KhBanDuocNgay]
(@Ngay date)
AS BEGIN
		SELECT sp.MaSP,sp.TenSP,DonGiaBan, dm.TenDanhMuc as LoaiSP,SoLuong as SLTonKho
		FROM SANPHAM sp
		inner join DanhMucSP dm on dm.MaDM = sp.MaDM
		WHERE sp.MASP not in ( SELECT hdct.MASP FROM HOADON hd,
		HoaDonChiTiet hdct
		WHERE hd.MaHDBan = hdct.MaHDBan AND (hd.NgayBan)=@Ngay)
		ORDER BY dm.TenDanhMuc
END
GO
/****** Object:  StoredProcedure [dbo].[sp_KhBanDuocThang]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_KhBanDuocThang]
(@Thang int)
AS BEGIN
		SELECT sp.MaSP,sp.TenSP,DonGiaBan, dm.TenDanhMuc as LoaiSP, SoLuong as SLTonKho
		FROM SANPHAM sp
		inner join DanhMucSP dm on dm.MaDM = sp.MaDM
		WHERE sp.MASP not in ( SELECT hdct.MASP FROM HOADON hd,
		HoaDonChiTiet hdct
		WHERE hd.MaHDBan = hdct.MaHDBan AND month(hd.NgayBan)=@Thang)
		ORDER BY dm.TenDanhMuc
END
GO
/****** Object:  StoredProcedure [dbo].[sp_NhanVien_CoDonNgay]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE  PROCEDURE [dbo].[sp_NhanVien_CoDonNgay] 
 @ngay date
 AS
	BEGIN
		select hd.MaHDBan,sum(hdct.SoLuong) as SoLuongSP,nv.MaNV,nv.TenNV,hd.NgayBan,SUM(((sp.DonGiaBan*hdct.SoLuong)*(100-hdct.GiamGia)/100)) as TongTien
		from HoaDonChiTiet hdct
		inner join SanPham sp on hdct.MaSP = sp.MaSP 
		inner join HoaDon hd on hd.MaHDBan = hdct.MaHDBan 
		inner join NhanVien nv on hd.MaNV = nv.MaNV
		where hd.NgayBan = @ngay and  hd.MaKH is not null
		group by  hd.MaHDBan,nv.MaNV,nv.TenNV,hd.NgayBan
	END
GO
/****** Object:  StoredProcedure [dbo].[sp_NhanVien_CoDonThang]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE  PROCEDURE [dbo].[sp_NhanVien_CoDonThang] 
 @thang int
 AS
	BEGIN
		select hd.MaHDBan,sum(hdct.SoLuong) as SoLuongSP,nv.MaNV,nv.TenNV,hd.NgayBan,SUM(((sp.DonGiaBan*hdct.SoLuong)*(100-hdct.GiamGia)/100)) as TongTien
		from HoaDonChiTiet hdct
		inner join SanPham sp on hdct.MaSP = sp.MaSP 
		inner join HoaDon hd on hd.MaHDBan = hdct.MaHDBan 
		inner join NhanVien nv on hd.MaNV = nv.MaNV
		where MONTH(hd.NgayBan) =@thang and  hd.MaKH is not null
		group by  hd.MaHDBan,nv.MaNV,nv.TenNV,hd.NgayBan
	END
GO
/****** Object:  StoredProcedure [dbo].[sp_NhanVienNgay]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_NhanVienNgay]
(@Ngay date)
AS BEGIN
	select MaNV,
		TenNV, 
	COUNT(DISTINCT MaHDBan) as SoHDBanDuoc,
	SUM(SPDaban) as SPDaBan,
	SUM(TongTien) as TongTienBanDuoc
from test where NgayBan = @Ngay
GROUP BY MaNV,TenNV	
END
GO
/****** Object:  StoredProcedure [dbo].[sp_NhanVienThang]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_NhanVienThang]
(@Thang int)
AS BEGIN
	select MaNV,
		TenNV, 
	COUNT(DISTINCT MaHDBan) as SoHDBanDuoc,
	SUM(SPDaban) as SPDaBan,
	SUM(TongTien) as TongTienBanDuoc
from test where Month(NgayBan) = @Thang
GROUP BY MaNV,TenNV	
END
GO
/****** Object:  StoredProcedure [dbo].[sp_ThongKeSanPhamDaBanTheoNgay]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_ThongKeSanPhamDaBanTheoNgay] 
@NgayBan date
AS
	BEGIN
		select  ct.MaHDBan, sp.MaSP,sp.TenSP,SUM(ct.SoLuong) as SoLuong, dmsp.TenDanhMuc,
		((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100) as ThanhTien
		from HoaDonChiTiet ct
		inner join SanPham sp on sp.MaSP = ct.MaSP  
		inner join HoaDon h on h.MaHDBan=ct.MaHDBan
		inner join DanhMucSP dmsp on dmsp.MaDM = sp.MaDM
		where ct.MaHDBan is not null and h.NgayBan=@NgayBan
		group by ct.MaHDBan, sp.MaSP,sp.TenSP,ct.SoLuong,h.NgayBan, dmsp.TenDanhMuc,((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_ThongKeSanPhamDaBanTheoThang]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_ThongKeSanPhamDaBanTheoThang] 
@Thang int
AS
	BEGIN
		select ct.MaHDBan, sp.MaSP,sp.TenSP,SUM(ct.SoLuong) as SoLuong, dmsp.TenDanhMuc,
		((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100) as ThanhTien
		from HoaDonChiTiet ct
		inner join SanPham sp on sp.MaSP = ct.MaSP  
		inner join HoaDon h on h.MaHDBan=ct.MaHDBan
		inner join DanhMucSP dmsp on dmsp.MaDM = sp.MaDM
		where ct.MaHDBan is not null and month (h.NgayBan)=@Thang
		group by ct.MaHDBan, sp.MaSP,sp.TenSP,ct.SoLuong,h.NgayBan, dmsp.TenDanhMuc,
		((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_Top5SanPham]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_Top5SanPham]
(@Ngay date)
AS BEGIN
		select	top 5
			sp.MaSP, sp.TenSP,sp.DonGiaBan,dmsp.TenDanhMuc as LoaiSP,SUM(hdct.SoLuong)as SoLuongDaBan

			from HoaDonChiTiet hdct
			inner join HoaDon hd on hd.MaHDBan = hdct.MaHDBan
			inner join SanPham sp on sp.MaSP = hdct.MaSP
			inner join DanhMucSP dmsp on dmsp.MaDM = sp.MaDM
			where hd.NgayBan = @Ngay
			GROUP BY sp.MaSP, sp.TenSP,sp.DonGiaBan,dmsp.TenDanhMuc
			ORDER BY SoLuongDaBan desc
END
GO
/****** Object:  StoredProcedure [dbo].[sp_Top5SanPhamTheoThang]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_Top5SanPhamTheoThang]
(@Thang int)
AS BEGIN
		select	top 5
			sp.MaSP, sp.TenSP,sp.DonGiaBan,dmsp.TenDanhMuc as LoaiSP,SUM(hdct.SoLuong)as SoLuongDaBan

			from HoaDonChiTiet hdct
			inner join HoaDon hd on hd.MaHDBan = hdct.MaHDBan
			inner join SanPham sp on sp.MaSP = hdct.MaSP
			inner join DanhMucSP dmsp on dmsp.MaDM = sp.MaDM
			where MONTH(hd.NgayBan) = @Thang
			GROUP BY sp.MaSP, sp.TenSP,sp.DonGiaBan,dmsp.TenDanhMuc
			ORDER BY SoLuongDaBan desc
END
GO
/****** Object:  Trigger [dbo].[trg_SuaHDCT]    Script Date: 12/15/2021 3:16:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[trg_SuaHDCT] on [dbo].[HoaDonChiTiet] after update AS
BEGIN
   UPDATE SanPham SET SoLuong = sp.SoLuong -
	   (SELECT SoLuong FROM inserted WHERE MaSP = sp.MaSP) +
	   (SELECT soluong FROM deleted WHERE MaSP = sp.MaSP)
   FROM SanPham sp 
   JOIN deleted ON sp.MaSP = deleted.MaSP
end
GO
ALTER TABLE [dbo].[HoaDonChiTiet] ENABLE TRIGGER [trg_SuaHDCT]
GO
/****** Object:  Trigger [dbo].[trg_ThemHDCT]    Script Date: 12/15/2021 3:16:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[trg_ThemHDCT] ON [dbo].[HoaDonChiTiet] AFTER INSERT AS 
BEGIN
	UPDATE SanPham 
	SET SoLuong = sp.SoLuong -(
		SELECT SoLuong
		FROM inserted
		WHERE MaSP = sp.MaSP
	)
	FROM SanPham sp
	JOIN inserted ON sp.MaSP = inserted.MaSP
END
GO
ALTER TABLE [dbo].[HoaDonChiTiet] ENABLE TRIGGER [trg_ThemHDCT]
GO
/****** Object:  Trigger [dbo].[trg_XoaHDCT]    Script Date: 12/15/2021 3:16:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create TRIGGER [dbo].[trg_XoaHDCT] ON [dbo].[HoaDonChiTiet] FOR DELETE AS 
BEGIN
	UPDATE SanPham
	SET SoLuong = sp.SoLuong + (SELECT SoLuong FROM deleted WHERE MaSP = sp.MaSP)
	FROM SanPham sp 
	JOIN deleted ON sp.MaSP = deleted.MaSP
END
GO
ALTER TABLE [dbo].[HoaDonChiTiet] ENABLE TRIGGER [trg_XoaHDCT]
GO

use master
go

/* câu lệnh tạo database */
Create database QuanLyBanHang
go
use QuanLyBanHang

------------------------------------------------------------------------------------------------
/* cập nhật số lượng sản phẩm trong kho sau khi thêm sản phẩm hoặc cập nhật hóa đơn chi tiết*/
if OBJECT_ID ('trg_ThemHDCT') is not null
	drop trigger trg_ThemHDCT
go

CREATE TRIGGER trg_ThemHDCT ON HoaDonChiTiet AFTER INSERT AS 
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
-------------------------------------------------------------------------------------------------
/* cập nhật sản phẩm trong kho sau khi cập nhật hóa đơn chi tiết */
if OBJECT_ID ('trg_SuaHDCT') is not null
	drop trigger trg_SuaHDCT
go

CREATE TRIGGER trg_SuaHDCT on HoaDonChiTiet after update AS
BEGIN
   UPDATE SanPham SET SoLuong = sp.SoLuong -
	   (SELECT SoLuong FROM inserted WHERE MaSP = sp.MaSP) +
	   (SELECT soluong FROM deleted WHERE MaSP = sp.MaSP)
   FROM SanPham sp 
   JOIN deleted ON sp.MaSP = deleted.MaSP
end
GO
-------------------------------------------------------------------------------------------------
/* cập nhật hàng trong kho sản phẩm sau khi xóa hóa đơn chi tiết */
if OBJECT_ID ('trg_XoaHDCT') is not null
	drop trigger trg_XoaHDCT
go

create TRIGGER trg_XoaHDCT ON HoaDonChiTiet FOR DELETE AS 
BEGIN
	UPDATE SanPham
	SET SoLuong = sp.SoLuong + (SELECT SoLuong FROM deleted WHERE MaSP = sp.MaSP)
	FROM SanPham sp 
	JOIN deleted ON sp.MaSP = deleted.MaSP
END
GO
-------------------------------------------------------------------------------------------------

/*Function tạo mã hóa đơn tự động tăng */	
if OBJECT_ID ('AUTO_MaHD') is not null
	drop function AUTO_MaHD
go
CREATE FUNCTION AUTO_MaHD()
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

--------------------------------------------------------------------------------
/*Function tạo mã nhân viên tự động tăng */	 
if OBJECT_ID ('AUTO_MaSP') is not null
	drop function AUTO_MaSP
go
CREATE FUNCTION AUTO_MaSP()
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
---------------------------------------------------------------------------------
/*Function tạo mã nhân viên tự động tăng */
if OBJECT_ID ('AUTO_MaNV') is not null
	drop function AUTO_MaNV
go
CREATE FUNCTION AUTO_MaNV()
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
------------------------------------------------------------------------------------
/*Function tạo mã khách hàng tự động tăng */
if OBJECT_ID ('AUTO_MaKH') is not null
	drop function AUTO_MaKH
go
CREATE FUNCTION AUTO_MaKH()
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

-----------------------------------------------------------------------------------------------------


/* câu lệnh tạo bảng khách hàng */
Create Table KhachHang(
	MaKH varchar(10) PRIMARY KEY CONSTRAINT MaKH DEFAULT [dbo].[AUTO_MaKH]() not null,
	TenKH nvarchar(50) not null,
	DiaChi nvarchar(50),
	Sdt varchar(20),
	Email varchar(50),
	Thanhvien bit,
);

/* câu lệnh tạo bảng danh mục sản phẩm*/
Create table DanhMucSP(
	MaDM int not null Identity (1,1),
	TenDanhMuc nvarchar(50) not null,
	primary key  (MaDM asc) 
);

/* câu lệnh tạo bảng sản phẩm*/
Create Table SanPham(
	MaSP varchar(10)  PRIMARY KEY CONSTRAINT MaSP DEFAULT [dbo].[AUTO_MaSP]() not null,
	TenSP nvarchar(50) not null,
	SoLuong int,
	DonGiaBan float,
	Anh varchar(max),
	GhiChu nvarchar(50),
	MaDM int,
	Size nvarchar(50),
);


/* câu lệnh tạo bảng nhân viên*/
Create Table NhanVien(
	MaNV varchar(10)  PRIMARY KEY CONSTRAINT MaNV DEFAULT [dbo].[AUTO_MaNV](),
	TenNV nvarchar(50) not null,
	GioiTinh bit,
	DiaChi nvarchar(50),
	DienThoai varchar(20),
	NgaySinh date,
	MatKhau varchar(30) not null,
	VaiTro bit,
);

/* câu lệnh tạo bảng hóa đơn*/
Create Table HoaDon(
	MaHDBan varchar(10) PRIMARY KEY CONSTRAINT MaHDBan DEFAULT [dbo].[AUTO_MaHD](),
	MaNV varchar(10) not null,
	NgayBan date,
	MaKH varchar(10)
);

/* câu lệnh tạo bảng hóa đơn chi tiet*/
Create Table HoaDonChiTiet(
	MaHDCT int Identity(1,10),
	MaHDBan varchar(10) not null,
	MaSP varchar(10) not null,
	SoLuong int,
	GiamGia float,
	primary key  (maHDCT asc) 
);

/*Insert dữ liệu vào bảng khách hàng */
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Nguyễn Văn Bình',N'Tam Kỳ - Quảng Nam','0372648176','binhnv01@gmail.com',1);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Bùi Thị Huyền',N'Tam Kỳ - Quảng Nam','0379177746','huyenhuyen@gmail.com',1);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Nguyễn Văn Tuấn Thành',N'Đà Nẵng','0135671600','thanhnvt23@gmail.com',0);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Huỳnh Thị Thu Huyền',N'Đà Nẵng','0371184009','thuhuyenhh@gmail.com',0);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Bùi Văn Thịnh',N'Quảng Nam','0905120023','thinhbv9182@gmail.com',0);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Trương Minh Hoàng',N'Quảng Nam','0903228094','hoangtruong2424@gmail.com',0);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Nguyễn Thanh Hưng',N'Đà Nẵng','0351120046','nguyenhung231@gmail.com',1);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Hồ Anh Quân',N'Quảng Nam','0391103758','anhquanho23@gmail.com',0);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Đinh Quý',N'Quảng Nam','0123470094','quydinh012@gmail.com',0);
insert into KhachHang values ([dbo].[AUTO_MaKH](),N'Trần Văn Tâm',N'Quảng Nam','0350148257','tranvantam27398@gmail.com',0);


/*Insert dữ liệu vào bảng danh mục sản phẩm*/
insert into DanhMucSP values (N'Áo');
insert into DanhMucSP values (N'Quần');
insert into DanhMucSP values (N'Mũ - nón');
insert into DanhMucSP values (N'Giày - dép');
insert into DanhMucSP values (N'Phụ kiện');
insert into DanhMucSP values (N'Khác');

/*Insert dữ liệu vào bảng sản phẩm */
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike Jordan 1 Pink',150,180,'No Image',N'Nike Jordan màu hồng',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike Jordan 1 Black',150,190,'No Image',N'Nike Jordan màu đen',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike Jordan 1 Chicago',200,250,'No Image',N'Nike Jordan Chicago',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike Jordan 1 Retro',200,250,'jd1retro.jpg',N'Nike Jordan màu Retro',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike Jordan 1 Off White',200,250,'No Image',N'Nike Jordan Off White',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike SB Dunk Pink',100,150,'No Image',N'Nike Sb Dunk màu hồng',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike SB Dunk Green',200,250,'No Image',N'Nike SB Dunk màu xanh',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike Air Force 1',120,200,'airforc1.jpg',N'Nike Air Force 1 full trắng',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike Jordan 1 Hight red',300,400,'jdhight1.jpg',N'Nike Jordan 1 cổ cao màu đỏ',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Nike Jordan 1 Hight Orange',300,400,'jdhight1.jpg',N'Nike Jordan 1 cổ cao màu cam',4,N'36-42');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Áo khoác Nike Blue',200,250,'ak.jpg',N'Màu xanh da trời',6,N'S-M-L-XL');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Mũ lưỡi trai MLB - Black',200,250,'No Image',N'Màu đen',3,N'S-M-L-XL');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'Mũ lưỡi trai MLB - Pink',200,250,'jdhight2.jpg',N'Màu hồng',3,N'S-M-L-XL');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'T-Shirt Nike x Bape',200,250,'No Image',N'Màu trắng',1,N'S-M-L-XL');
insert into SanPham values ([dbo].[AUTO_MaSP](),N'T-Shirt Nike x Bape',200,250,'No Image',N'Màu trắng',1,N'S-M-L-XL');




/*Insert dữ liệu vào bảng nhân viên */
insert into NhanVien values ([dbo].[AUTO_MaNV](),N'Trương Minh Hiếu',1,N'Quảng Nam','0372795594','20020421','123',1);
insert into NhanVien values ([dbo].[AUTO_MaNV](),N'Hồ Đắc Danh',1,N'Quảng Nam','0378492759','20000504','123',0);
insert into NhanVien values ([dbo].[AUTO_MaNV](),N'Bùi Thị Xuân Hồng',0,N'Đà Nẵng','0358250091','19991205','123',0);
insert into NhanVien values ([dbo].[AUTO_MaNV](),N'Nguyễn Văn Hoàng',1,N'Quảng Nam','0328617453','20011020','123',0);
insert into NhanVien values ([dbo].[AUTO_MaNV](),N'Huỳnh Văn Thái Nguyên',1,N'Đà Nẵng','0905173520','20021021','123',0);

/*Insert dữ liệu vào bảng hóa đơn */
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV002','20210113','KH001');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV002','20210113','KH003');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV002','20210116','KH009');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV001','20210113','KH004');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV001','20211013','KH005');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV004','20211013','KH009');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV004','20211113','KH007');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV005','20211117','KH009');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV003','20211119','KH008');
insert into HoaDon values ([dbo].[AUTO_MaHD](),'NV003','20211020','KH009');


/*Insert dữ liệu vào bảng hóa đơn chi tiết */
insert into HoaDonChiTiet values ('HD00001','SP001',2,0);
insert into HoaDonChiTiet values ('HD00002','SP001',1,0);
insert into HoaDonChiTiet values ('HD00003','SP002',1,0);;
insert into HoaDonChiTiet values ('HD00004','SP010',1,0)
insert into HoaDonChiTiet values ('HD00005','SP010',2,0);
insert into HoaDonChiTiet values ('HD00006','SP005',1,0);
insert into HoaDonChiTiet values ('HD00007','SP003',3,0);
insert into HoaDonChiTiet values ('HD00008','SP006',1,0);
insert into HoaDonChiTiet values ('HD00009','SP009',1,0);
insert into HoaDonChiTiet values ('HD00010','SP007',2,0);
insert into HoaDonChiTiet values ('HD00001','SP005',2,0);


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

ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK_DanhMuc_SanPham] FOREIGN KEY([MaDM])
REFERENCES [dbo].[DanhMucSP] ([MaDM])
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK_DanhMuc_SanPham]
GO
------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------
/*proc gọi ra hóa đơn chi tiết theo mã hóa đơn bán*/
if OBJECT_ID ('sp_HDCT_MaHD') is not null
	drop proc sp_HDCT_MaHD
go
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
exec [dbo].[sp_HDCT_MaHD] 'HD01'


 ---------------------------------------------------------------------------
 /*Thống kê số sản phẩm đã bán theo tháng */
if OBJECT_ID ('sp_ThongKeSanPhamDaBanTheoThang') is not null
	drop proc sp_ThongKeSanPhamDaBanTheoThang
go
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
exec [dbo].[sp_ThongKeSanPhamDaBanTheoThang] 11 --Thực thi proc
 ---------------------------------------------------------------------------

 /*Thống kê số sản phẩm đã bán theo ngày */
if OBJECT_ID ('sp_ThongKeSanPhamDaBanTheoNgay') is not null
	drop proc sp_ThongKeSanPhamDaBanTheoNgay
go

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
exec [sp_ThongKeSanPhamDaBanTheoNgay] '20210113'



----------------------------------------------------------------------------------------------------------------
/****** Object:  StoredProcedure [dbo].[ThongKeNhanVien_CoDon]    Script Date: 10/29/2021 2:44:04 PM ******/
if OBJECT_ID ('sp_NhanVien_CoDonNgay') is not null
	drop proc sp_NhanVien_CoDonNgay
go
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

exec sp_NhanVien_CoDonNgay '20210113'

----------------------------------------------------------------------------------------------------------------
/****** Object:  StoredProcedure [dbo].[ThongKeNhanVien_CoDon]    Script Date: 10/29/2021 2:44:04 PM ******/
/* thống kê nhân viên có đơn theo tháng */

if OBJECT_ID ('sp_NhanVien_CoDonThang') is not null
	drop proc sp_NhanVien_CoDonThang
go
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

exec sp_NhanVien_CoDonThang 11

/*proc gọi ra tất cả hóa đơn */
if OBJECT_ID ('sp_HoaDon') is not null
	drop proc sp_HoaDon
go
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
exec sp_HoaDon

---------------------------------------------------------------------------------------
/*proc gọi ra tất cả hóa đơn theo ngày */
if OBJECT_ID ('sp_HoaDonTheoNgay') is not null
	drop proc sp_HoaDonTheoNgay
go
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

exec sp_HoaDonTheoNgay '20211207'
--------------------------------------------
/*Tính doanh thu theo ngày */
if OBJECT_ID ('sp_DoanhThuTheoNgay') is not null
	drop proc sp_DoanhThuTheoNgay
go
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


exec [dbo].[sp_DoanhThuTheoNgay] '2021-01-13'
----------------------------------------------------------------------------------------------------------------
/*Thống kê doanh thu theo tháng */
if OBJECT_ID ('sp_DoanhThuTheoThang') is not null
	drop proc sp_DoanhThuTheoThang
go
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

exec [dbo].[sp_DoanhThuTheoThang] 11
---------------------------------------------------------------------------
/* Procedure danh sách các sản phẩm (MASP,TENSP,DonGiaBan) không bán được theo tháng */
if OBJECT_ID ('sp_KhBanDuocThang') is not null
	drop proc sp_KhBanDuocThang
go
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

exec [dbo].[sp_KhBanDuocThang] 11

-----------------------------------------------------------------------------------
/* Procedure danh sách các sản phẩm (MASP,TENSP,DonGiaBan) không bán được theo tháng */
if OBJECT_ID ('sp_KhBanDuocNgay') is not null
	drop proc sp_KhBanDuocNgay
go
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

exec sp_KhBanDuocNgay '20211120'
-----------------------------------------------------------------------------

/* proc top 5 sản phẩm bán chạy nhất theo ngày */

if OBJECT_ID ('sp_Top5SanPham') is not null
drop proc sp_Top5SanPham
go
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

exec sp_Top5SanPham '20211209'

/* proc top 5 sản phẩm bán chạy nhất theo tháng */

if OBJECT_ID ('sp_Top5SanPhamTheoThang') is not null
drop proc sp_Top5SanPhamTheoThang
go
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

exec sp_Top5SanPhamTheoThang 11
--------------------------------------------------------------------------------
/*thống kê nhân viên bán hàng theo tháng */
if OBJECT_ID ('sp_NhanVienThang') is not null
	drop proc sp_NhanVienThang
go
CREATE PROC [dbo].sp_NhanVienThang
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

exec [dbo].[sp_NhanVienThang] 11
----------------------------------------------------------------------------------------------------

/*thống kê nhân viên bán hàng theo ngày */
if OBJECT_ID ('sp_NhanVienNgay') is not null
	drop proc sp_NhanVienNgay
go
CREATE PROC [dbo].sp_NhanVienNgay
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

exec [dbo].[sp_NhanVienNgay] '20211209'

------------------------------------------------------------------------------------------------------------



------------------------------------------------------------------------------------------------------------

/*create view dùng để tính doanh thu*/

if OBJECT_ID ('DoanhThu_View') is not null
	drop view DoanhThu_View
go

CREATE VIEW DoanhThu_View As

select hd.MaHDBan,hd.NgayBan, SUM(((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100)) as TongTien,
SUM(ct.SoLuong) as SPDaBan

from HoaDon hd 
		inner join HoaDonChiTiet ct on ct.MaHDBan = hd.MaHDBan
		inner join SanPham sp on sp.MaSP = ct.MaSP
GROUP BY hd.MaHDBan, hd.NgayBan

select * from HoaDon
--------------------------------------------------------------------------------------------------------------------
/*View xem doanh thu */

if OBJECT_ID ('test') is not null
	drop view test
go
CREATE VIEW test As

select hd.MaHDBan,hd.NgayBan,nv.MaNV,nv.TenNV, SUM(((sp.DonGiaBan*ct.SoLuong)*(100-ct.GiamGia)/100)) as TongTien,
SUM(ct.SoLuong) as SPDaBan

from HoaDon hd 
		inner join HoaDonChiTiet ct on ct.MaHDBan = hd.MaHDBan
		inner join SanPham sp on sp.MaSP = ct.MaSP
		inner join NhanVien nv on nv.MaNV = hd.MaNV
GROUP BY hd.MaHDBan, hd.NgayBan,nv.MaNV,nv.TenNV


/*Thêm mới dữ liệu vào bảng*/
INSERT INTO NhanVien VALUES ([dbo].[AUTO_MaNV](), ?, ?, ?, ?, ?, ?, ?);

/*Cập nhật dữ liệu theo mã truyền vào*/
UPDATE NhanVien SET TenNV =?, GioiTinh=? ,DiaChi=? ,DienThoai=? ,NgaySinh=? , MatKhau=? , VaiTro=? WHERE MaNV = ?;

/*Xóa dữ liệu theo mã truyền vào*/
DELETE FROM NhanVien WHERE MaNV = ?

/*Truy vẫn dữ liệu theo mã truyền vào*/
SELECT * FROM NhanVien WHERE MaNV=?

/*Truy vấn tất cả thông tin */
SELECT * FROM NhanVien

# Tanahku V1 - Backend

## Deskripsi Proyek

Proyek ini bertujuan untuk mengembangkan sebuah sistem Internet of Things (IoT) yang terintegrasi dengan RESTful API untuk memonitor kondisi lahan pertanian. Sistem ini akan memungkinkan pengguna untuk memantau berbagai parameter dan kondisi lingkungan yang berkaitan dengan pertanian, seperti kelembaban tanah, suhu udara, tingkat kelembaban udara, cahaya matahari, dan lain-lain.

## Arsitektur Sistem:

Sistem akan terdiri dari komponen-komponen berikut:

- Perangkat IoT: Sensor dan perangkat lainnya yang terhubung ke lahan pertanian untuk mengukur kondisi lingkungan.

- Backend RESTful API: Mengelola pengambilan data dari perangkat IoT, penyimpanan data, dan menyediakan antarmuka untuk pengguna untuk mengakses data.

- Database: Menyimpan data pemantauan dan informasi konfigurasi sistem.

- Antarmuka Pengguna: Antarmuka web atau aplikasi mobile yang memungkinkan pengguna untuk memantau data, mengelola perangkat, dan menerima notifikasi.

## Instalasi

Berikut adalah langkah-langkah untuk menginstal dan menjalankan proyek:

1. **Klon Repositori:**
    ```bash
    git clone https://github.com/maling-it/tanahku-be.git
    ```

2. **Navigasi ke Direktori Proyek:**
    ```bash
    cd tanahku-be
    ```
3. **Jalankan Aplikasi:**
    - Menggunakan Maven:
      ```bash
      mvn spring-boot:run
      ```

    - Jika Belum Menginstall maven:
      ```bash
      sudo apt install maven
      ```
      ```bash
      mvn --version
      ```

## Konfigurasi
1. **Java Version:**
    ```bash
    <java.version>17</java.version>
    ```
2. **Spring-Boot Version:**
    ```bash
    <version>3.1.8</version>
    ```
3. **Konfigurasi proyek seperti database dan lain-lain di file *application.properties*:**
    ```bash
		# Port used
		server.port= 8888

		# Configuration with mysql database
		spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
		spring.datasource.username=username
		spring.datasource.password=password
		spring.datasource.url=jdbc:mysql://localhost:3306/tanahku
		spring.datasource.type=com.zaxxer.hikari.HikariDataSource
		spring.datasource.hikari.minimum-idle=10
		spring.datasource.hikari.maximum-pool-size=50

		spring.jpa.open-in-view=true

		# spring.jpa.properties.hibernate.format_sql=true
		# spring.jpa.properties.hibernate.show_sql=true

		#Auto create table
		# spring.jpa.hibernate.ddl-auto = update
    ```

4. **Dependency:**
    ```bash
    <dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
	</dependencies>
    ```

## Penggunaan API

Untuk penggunaan API, bisa dilihat pada folder docs.



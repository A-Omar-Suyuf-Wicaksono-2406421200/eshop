# MODUL 2
## Reflection
1. Dari hasil PMD scan ditemukan beberapa violations di codebase.
Issue yang saya fix adalah penggunaan modifier `public` yang tidak
perlu pada method-method di interface `ProductService.java`. Di Java,
semua method dalam interface sudah implicitly public, jadi penulisan
`public` secara eksplisit itu redundant dan dianggap bad practice.
Maka dengan saya menghapus `public` dari semua method
di interface tersebut, PMD violations berkurang dari 43 menjadi 38.

2. Implementasi yang saya buat sudah memenuhi definisi CI/CD. Untuk
Continuous Integration, setiap kali ada push atau pull request ke
repository, GitHub Actions secara otomatis menjalankan unit test
menggunakan Gradle dan menghasilkan code coverage report lewat Jacoco.
DAn juga ada workflow PMD yang otomatis berjalan untuk
menganalisis kualitas kode dan mendeteksi violations setiap kali ada
perubahan kode. Dengan adanya otomasi testing ini dan code scanning,
proses integrasi kode menjadi lebih terjamin kualitasnya tanpa harus
dilakukan secara manual setiap saat.

# MODUL 1

## Reflection 1

Dalam mengimplementasikan fitur edit dan delete product, 
saya mengalami beberapa tantangan. 
Awalnya sebelum memulai implementasi fitur baru, 
saya menemukan beberapa error di codenya.
Ternyata masalahnya simpel tapi agak ceroboh, 
yaitu ada typo pada import statement dimana tertulis 
Repositry padahal seharusnya Repository.

Setelah itu proses pengerjaan exercise dimulai dengan membuat fungsi-fungsi di 
backend terlebih dahulu, yaitu di layer Repository, Service,
dan Controller. Awalnya saya pikir sudah selesai, tapi ternyata 
saat mencoba menjalankan aplikasi masih muncul error. 
Setelah dicek lebih lanjut, ternyata saya lupa membuat file 
HTML untuk halaman edit product, dan juga lupa menambahkan getter/setter 
untuk productId di Model Product. Setelah melengkapi kedua bagian 
yang kurang tersebut, akhirnya fitur edit dan delete berfungsi dengan baik.


## Reflection 2
1. Unit Testing dan Code Coverage
Setelah menulis unit test untuk fitur edit dan delete product, 
saya rasa unit test membantu memverifikasi bahwa setiap fungsi 
berjalan sesuai ekspektasi tanpa harus menjalankan seluruh aplikasi. 
Mengenai berapa banyak unit test yang harus dibuat, 
saya belajar bahwa idealnya setiap method public di Service layer perlu ditest, 
termasuk skenario positive case dan negative case.
Code coverage adalah metrik yang menunjukkan seberapa banyak kode kita 
yang sudah tercover oleh test. Idealnya code coverage sekitar 
80-100% cukup bagus, tapi bukan berarti 100% coverage menjamin tidak ada bug.

2. Code Cleanliness dalam Functional Test
Ketika diminta membuat functional test baru untuk verifikasi 
jumlah items di product list, saya baru sadar ada beberapa isu 
terkait code cleanliness. Jika kita membuat test baru dengan setup 
procedures yang sama , kita akan melakukan semacam code duplication. 
Ini melanggar prinsip Don't Repeat Yourself.

Masalah potensial yang bisa muncul:
Code Duplication: Setup procedures yang sama ditulis berulang-ulang di setiap test class
Hard to Maintain: Jika ada perubahan di setup process, harus update di banyak tempat
Inconsistency: Beda test class bisa punya setup yang sedikit berbeda, 
menyebabkan hasil test yang tidak konsisten

Solusi yang lebih baik adalah membuat base class atau helper class 
yang berisi common setup procedures, lalu semua functional test class 
bisa extends atau menggunakan helper tersebut. Jadi ketika
ada perubahan di setup process, kita hanya perlu update di satu tempat saja. 
Ini membuat kode lebih clean, maintainable, dan konsisten antar test.
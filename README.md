Reflection 1

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


Reflection 2
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
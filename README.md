# Resticopia

[![F-Droid](https://img.shields.io/f-droid/v/org.dydlakcloud.resticopia.svg)](https://f-droid.org/packages/org.dydlakcloud.resticopia/)
[![GNU General Public License, Version 2](https://img.shields.io/github/license/lhns/restic-android.svg?maxAge=3600)](https://www.gnu.org/licenses/gpl-2.0.html)
[![Build Status](https://codeberg.org/dawdyd/resticopia/badges/workflows/build.yml/badge.svg)](https://codeberg.org/dawdyd/resticopia/actions)
[![Code Quality](https://codeberg.org/dawdyd/resticopia/badges/workflows/code-quality.yml/badge.svg)](https://codeberg.org/dawdyd/resticopia/actions)

 <a href="https://f-droid.org/packages/org.dydlakcloud.resticopia/">
    <img src="docs/badges/fdroid-get-it-on.svg" alt="Get it on F-Droid" width="160"/>
  </a>
 <a href="https://apps.obtainium.imranr.dev/redirect?r=obtainium://add/https://codeberg.org/dawdyd/resticopia">
    <img src="docs/badges/obtainium.png" alt="Obtainium" width="160"/>
  </a>

## Screenshots

<p>
  <img src="screenshots/2.png" width="175" alt="Repository Setup">
  <img src="screenshots/3.png" width="175" alt="Encrypted Backups">
  <img src="screenshots/4.png" width="175" alt="Folder Snapshots">
  <img src="screenshots/5.png" width="175" alt="Scheduled Backups">
  <img src="screenshots/6.png" width="175" alt="Browse & Restore">
</p>

## About

A mobile Android application that enables efficient and straightforward data backups powered by [Restic](https://restic.net) backup software.

The application uses a custom-built [PRoot](https://codeberg.org/dawdyd/build-proot-android) environment (based on the [build-proot-android](https://github.com/green-green-avk/build-proot-android) project) to run native [Restic](https://restic.net) and [Rclone](https://rclone.org) binaries directly on Android devices.

### Disclaimer
This is an **unofficial** application and is not developed or endorsed by the official Restic project team.

## Key Capabilities
- Repository Management: Create and configure Restic repositories (supports S3, B2, Rest, Local, and Rclone protocols)
- Snapshot Control: Browse and manage your backup snapshots
- Folder Selection: Choose which directories to include in backups
- Automated Scheduling: Set up recurring backup tasks
- Retention Policies: Define cleanup rules for individual folders
- Live Progress: Monitor backup operations through system notifications
- Rclone Integration: Access 40+ cloud storage providers via Rclone backend
- Webhook Notifications: Monitor backup status via HTTP webhooks (supports Gatus, and more) ([docs](docs/webhook.md))

## Donate

<a href="https://buymeacoffee.com/dawdyd">
  <img src="docs/badges/bmc_button.png" alt="Buy Me a Coffee" width="180"/>
</a>

### This Project
- All modifications and additions are also licensed under GNU General Public License v2.0
- See git commit history for detailed changes

#### Các Cải tiến & Sửa lỗi Chính trong Bản Fork này
- **Cải tiến Giao diện & Trải nghiệm Người dùng (UX & UI)**:
  - **Sửa lỗi Chọn Remote Rclone**: Khắc phục lỗi khi chọn Remote Rclone có chứa định dạng loại trong ngoặc đơn (ví dụ: `remote (s3)`) bị lưu thành chuỗi rỗng `""` gây mất kết nối. Bộ phân tích hiện tại sẽ tự động tách lấy tên Remote gốc.
  - **Loại bỏ Bộ lọc Hostname**: Loại bỏ bộ lọc tên thiết bị mặc định khi hiển thị danh sách bản sao lưu (snapshots) ở cả màn hình kho lưu trữ và màn hình thư mục. Người dùng hiện có thể xem và khôi phục các bản sao lưu từ bất kỳ thiết bị nào mà không cần cấu hình hostname thủ công.
- **Tối ưu hóa Quy trình CI/CD & Build (GitHub Actions)**:
  - **Tự động Biên dịch Native Binaries**: Tích hợp tự động biên dịch các file nhị phân gốc (PRoot, Restic, Rclone) thông qua Docker Compose ngay trong các workflow của GitHub Actions, giúp tránh lỗi runtime "không tìm thấy tệp tin" trên file APK đầu ra.
  - **Sửa lỗi Phân quyền & VCS**: Bổ sung cờ `-buildvcs=false` và cấu hình `safe.directory` cho Git bên trong Docker container để sửa các lỗi phân quyền sở hữu Git khi biên dịch.
  - **Khôi phục Quyền sở hữu trên Host**: Khắc phục lỗi phân quyền ghi bằng cách trả lại quyền sở hữu (`chown`) cho user trên máy ảo đối với tất cả các thư mục được sinh ra từ Docker.
  - **Cơ chế Tự động Chuyển đổi Khóa Ký (Keyless Build Fallback)**: Tự động chuyển sang build bản APK debug (`assembleDebug`) nếu các secrets cấu hình khóa ký chính thức không tồn tại, giúp tiến trình build không bị lỗi.
  - **Cấp quyền ghi cho Release Assets**: Khai báo rõ ràng quyền `contents: write` trong workflow phát hành để cho phép đẩy các tệp APK trực tiếp lên mục GitHub Releases.

### Original Work
- **Original Project**: [restic-android](https://github.com/lhns/restic-android) by [lhns](https://github.com/lhns)
- **Original License**: GNU General Public License v2.0

## Notice
See the file called NOTICE.

## License
This project uses the GNU General Public License, Version 2. See the file called LICENSE.

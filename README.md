# Arkanoid
- Diagram: https://drive.google.com/file/d/1i6mOp430AFdbZ5EpiFPIy1IMDZiCM1BC/view?usp=sharing
- Link game demo: https://drive.google.com/drive/folders/1k_dAR30PbadPol92esx_7GMEtltul9k0?usp=drive_link
- Danh gia ca nhan: https://docs.google.com/spreadsheets/d/1fsqVL5rvPGrfDf6cypOoQJjGr2_iToTVoZbzhdyqjpE/edit?fbclid=IwY2xjawN1J7xleHRuA2FlbQIxMABicmlkETFNTDlZWlRmb1cwVDhPQ0NRAR54ek2EhcBo0tKVMITFah0DmqSRwOoj5bj265P9Di-zVeBXmJcqpRALLz81cQ_aem_pIYEJK7St2xpQ_3pzlh-Bg&gid=0#gid=0

Arkanoid Group Project (OOP)
- Hoàng Trung Dũng 24020084
- Phạm Chí Dũng 24020093
- Nguyễn Thành Long 24020210
- Trần Doãn Hải Đăng 24020057

Start date: 29/9/2025

________________________________________
Đồ án Game: Arkanoid
## 🚀 Các tính năng chính
- Nhiều màn chơi (Levels) với độ khó tăng dần (STAGE_1, STAGE_2, STAGE_3).  
- Các loại gạch:
  - NormalBrick – Gạch thường, có thể bị phá.  
  - IndestructibleBrick – Gạch không thể phá.  
- Hệ thống Power-Up & De-Buff (9 loại vật phẩm, có thời gian hiệu lực).  
- Âm thanh chân thực (va chạm, mất mạng, Power-Up,...).  
- Game chạy đa luồng (multi-threading).  
- Hệ thống điểm & mạng (Score, Lives, High Score).  
- Người chơi có thể **bắn đạn (Ammo)** bằng chuột.  

---

## 🖥️ Giao diện người dùng
### Màn hình chính
- Menu – Giao diện chính của trò chơi  
- LevelPlay – Chọn màn chơi  
- LoadScreen – Màn hình tải  
- Setting – Cài đặt  
- Instruction / Help – Hướng dẫn chơi  
- Power_Up_View – Thông tin vật phẩm  

### Giao diện trong game
- Hiển thị Score và Lives  
- Khi nhận Power-Up, hiển thị thanh thời gian hiệu lực  

---

## 🧱 Cấu trúc màn chơi (Level Design)
- Gồm nhiều màn chơi với bố cục khác nhau  
- Dữ liệu màn chơi được tải từ:
  - `MapLoader.loadBricksFromTiled();`

---

## 🔹 Hệ thống gạch (Bricks)
### Phân loại
- NormalBrick – Gạch thường, có thể bị phá  
- IndestructibleBrick – Gạch không thể phá  

### Điểm số
- Mỗi viên gạch bị phá cho điểm:  
Score += brick.getMaxHp() * 10

markdown
Sao chép mã

---

## ⚡ Hệ thống Power-Up
Game có **9 loại vật phẩm**, chia thành 2 nhóm:

### Buffs
- SuperBallPowerUp – Tăng kích thước và tốc độ bóng  
- InvincibleBallPowerUp – Bóng bất tử, xuyên gạch  
- MultiBallPowerUp – Tạo thêm bóng phụ  
- ExtraLifePowerUp – Thêm mạng  
- DoubleScorePowerUp – Nhân đôi điểm  
- RespawnFreePowerUp – Miễn trừ 1 lần mất mạng  
- ExtendPaddle – Mở rộng paddle  

### De-Buffs
- HarderBrickPowerDown – Gạch cứng hơn  
- ShrinkPaddle – Thu nhỏ paddle  

### Cơ chế
- Power-Up rơi ngẫu nhiên khi phá gạch (tỉ lệ nhất định)  
- Thời gian hiệu lực giới hạn (ví dụ: 3 giây)  
- Hiển thị thanh tiến trình (progress bar) khi hiệu ứng hoạt động  
- Theo chu kỳ sẽ có **Boss** và **Lightning** xuất hiện:  
- Boss sinh ra báo hiệu gạch di chuyển xuống  
- Lightning xuất hiện kèm hiệu ứng mây  

---

## 🔊 Hệ thống âm thanh (Audio)
Sử dụng lớp `AudioSet` để quản lý toàn bộ âm thanh:  

- **Âm va chạm:**  
- wallBounceSound – Bóng chạm tường  
- collisionBrickSound – Bóng chạm gạch  
- collisionPaddleSound – Bóng chạm paddle  

- **Âm hiệu ứng:**  
- powerUpSound – Nhận Power-Up  
- lossHpSound – Mất mạng  
- gameOverSound – Khi Game Over  

---

## 🕹️ Cơ chế Gameplay
### Cơ bản
- Di chuyển paddle: **A** (trái), **D** (phải)  
- Nhấn **Space** để phóng bóng  
- Bóng rơi khỏi màn → mất 1 mạng  
- Hết mạng → Game Over  
- Phá hết gạch → Thắng màn chơi  
- **ESC** để tạm dừng game  

### Điểm & mạng
- Mỗi màn có số mạng khởi đầu riêng (VD: Màn 1 – 5 mạng, Màn 2 – 8 mạng)  
- Lưu **Highest Score** đạt được  

### Cơ chế đặc biệt
- **Bắn đạn (Ammo):** Nhấn chuột để bắn, trừ 20 điểm mỗi phát  
- **AI Mode:** Nhấn phím **S** để bật/tắt tự động điều khiển paddle  
- Nếu bị kẹt, nhấn **R** để reset vị trí (mất 1 mạng)  

---

## 🧠 Chế độ chơi
### 1. Chế độ 1 người
- Paddle, bóng, gạch, điểm, mạng hiển thị đầy đủ  
- Mục tiêu: đạt điểm cao nhất có thể  

### 2. Chế độ AI
- Nhấn **S** để bật/tắt  
- Paddle tự động di chuyển theo bóng  

---

## 🏗️ Cấu trúc chương trình

- **GameManager:**  
Lớp chính của JavaFX. Quản lý vòng lặp game (`AnimationTimer`), trạng thái (`GameState`), xử lý input.  

- **GameSetup:**  
Quản lý khởi tạo màn chơi, danh sách bricks, balls, paddles; xử lý điểm, mạng, điều kiện thắng/thua.  

- **Ball:**  
Kế thừa `MovableObject`. Quản lý di chuyển, va chạm với gạch, paddle, tường.  

- **Paddle:**  
Kế thừa `MovableObject`. Quản lý di chuyển (player hoặc AI thông qua `MovementStrategy`).  

- **Boss:**  
Sinh ra cảnh báo brick rơi xuống. Logic xử lý trong `GameManager` và `GameSetup`.  

- **Lightning:**  
Sinh ra mây và sấm sét tại vị trí xác định.  

- **Brick:**  
Lớp cha cho các loại gạch, quản lý độ cứng (`hitPoints`).  

- **PowerUp:**  
Lớp cha cho tất cả vật phẩm, quản lý thời gian hiệu lực (`effectDurationMillis`) và hiệu ứng.  

- **GameView (và các View khác):**  
Quản lý vẽ (render) các đối tượng và giao diện trên `GraphicsContext (Canvas)`.

---

## 🖼️ GameView
- Chịu trách nhiệm hiển thị toàn bộ thành phần đồ họa trong game  
- Bao gồm điểm số, mạng, Power-Up và thanh hiệu ứng  

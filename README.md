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

1. Giới thiệu chung
Arkanoid là một trò chơi hành động – phản xạ thuộc thể loại Breakout cổ điển, nơi người chơi điều khiển paddle để đánh bóng (ball) phá các viên gạch (bricks).
Phiên bản Arkanoid này được phát triển bằng Java 17 và JavaFX, bổ sung nhiều tính năng hiện đại và phong phú, bao gồm:
Nhiều màn chơi (Levels): Có nhiều màn chơi với độ khó tăng dần (ví dụ: STAGE_1, STAGE_2, STAGE_3).
Các loại gạch: Gồm gạch thường (NormalBrick) và gạch không thể phá (IndestructibleBrick).
Hệ thống Power-Up & De-Buff: Bao gồm 9 loại vật phẩm đa dạng với thời gian hiệu lực.
Âm thanh chân thực: Gồm âm thanh va chạm (với tường, gạch, paddle), âm thanh hiệu ứng (mất mạng, nhận power-up).
Chế độ chơi: Chế độ 1 người chơi.
Hệ thống điểm, mạng: Lưu điểm (Score), số mạng (Lives) và lưu điểm cao nhất (High Score).
Tính năng độc đáo: Người chơi có thể bắn đạn (Ammo) bằng cách nhấn chuột.
2. Giao diện người dùng
2.1. Màn hình chính
Game có nhiều màn hình chức năng:
Menu: Màn hình chính.
LevelPlay: Giao diện chọn màn chơi.
LoadScreen: Giao diện tải game (nếu có).
Setting: Cài đặt game.
Instruction / Help: Hướng dẫn chơi.
Power_Up_View: Hiển thị thông tin các vật phẩm.
2.2. Giao diện trong game
Hiển thị điểm (Score) và mạng (Lives).
Khi nhận Power-Up, hiển thị thanh thời gian hiệu lực của vật phẩm đó.
3. Cấu trúc màn chơi (Level Design)
Gồm nhiều màn chơi (ví dụ: STAGE_1, STAGE_2, STAGE_3).
Mỗi màn có bố cục gạch khác nhau, được tải từ các tệp bản đồ (sử dụng MapLoader.loadBricksFromTiled).
4. Gạch
4.1. Phân loại
NormalBrick: Gạch thường, có thể bị phá.
IndestructibleBrick: Gạch không thể phá.
4.2. Điểm số
Mỗi viên gạch bị phá sẽ cho điểm, tính dựa trên maxHp của gạch (ví dụ: brick.getMaxHp() * 10).
5. Power-Up
Game có 9 loại vật phẩm, được chia làm 2 nhóm:
5.1. Buffs
SuperBallPowerUp: (Không rõ hiệu ứng từ mã nguồn).
InvincibleBallPowerUp: Bóng bất tử, phá gạch không nảy lại.
MultiBallPowerUp: Tạo thêm các bóng phụ.
ExtraLifePowerUp: Cộng thêm 1 mạng.
DoubleScorePowerUp: Nhân đôi điểm nhận được.
RespawnFreePowerUp: (Không rõ hiệu ứng từ mã nguồn).
ExtendPaddle: Mở rộng paddle.
5.2. De-Buffs
HarderBrickPowerDown: Gạch trở nên khó phá hơn.
ShrinkPaddle: Thu nhỏ paddle.
5.3. Cơ chế
Power-Up rơi ra khi phá gạch (với một tỷ lệ nhất định).
Mỗi Power-Up có hiệu lực trong một khoảng thời gian (ví dụ: 3 giây) và có thanh hiển thị thời gian.
6. Âm thanh
Game sử dụng lớp AudioSet để quản lý âm thanh:
Âm va chạm: Phát khi bóng chạm tường (wallBounceSound), chạm gạch (collisionBrickSound), hoặc chạm paddle (collisionPaddleSound).
Âm hiệu ứng: Phát khi nhận Power-Up (powerUpSound), mất mạng (lossHpSound), hoặc Game Over (gameOverSound).
7. Cơ chế gameplay
7.1. Cơ bản
Người chơi điều khiển paddle bằng phím A (Trái) và D (Phải).
Nhấn Space để phóng bóng.
Bóng rơi khỏi màn hình → mất 1 mạng.
Khi hết tất cả mạng → Game Over.
Khi phá hết gạch có thể phá → Thắng màn chơi.
Nhấn Escape để tạm dừng (Pause) game.
7.2. Cơ chế điểm và mạng
Người chơi khởi đầu với số mạng tùy theo màn (ví dụ: Màn 1 là 5 mạng, Màn 2 là 8 mạng).
Game lưu lại Highest Score (điểm cao nhất đạt được).
7.3. Cơ chế đặc biệt
Bắn đạn: Người chơi có thể nhấn chuột để bắn đạn (Ammo), mỗi lần bắn tốn 20 điểm.
Chế độ AI: Người chơi có thể nhấn phím S để bật/tắt chế độ AI tự động điều khiển paddle.
8. Chế độ chơi
8.1. Chế độ 1 người
Màn hình hiển thị: Paddle, bóng, gạch, điểm, số mạng.
Mục tiêu: Đạt điểm cao nhất và so sánh với Highest Score.
8.2. Chế độ AI
Kích hoạt bằng phím S.
Paddle sẽ tự động di chuyển theo bóng.
9. Cấu trúc chương trình
GameManager: Lớp Application chính của JavaFX, quản lý vòng lặp game (AnimationTimer), các trạng thái game (GameState), xử lý input (phím, chuột).
GameSetup: Khởi tạo và quản lý các đối tượng trong màn chơi (danh sách bricks, balls, paddles), quản lý logic điểm, mạng, và điều kiện thắng/thua.
Ball: Kế thừa MovableObject. Quản lý logic di chuyển, va chạm với gạch/paddle/tường, và góc bắn.
Paddle: Kế thừa MovableObject. Quản lý logic di chuyển. Sử dụng MovementStrategy (hoặc PlayerMovement hoặc AIMovement) để quyết định cách di chuyển.
Brick: Lớp cha cho các loại gạch, quản lý độ cứng (hitPoints).
PowerUp: Lớp cha cho tất cả vật phẩm, quản lý thời gian hiệu lực (effectDurationMillis) và logic áp dụng/gỡ bỏ hiệu ứng.
GameView (và các lớp View khác): Quản lý việc vẽ (render) các đối tượng và giao diện lên GraphicsContext (Canvas). 

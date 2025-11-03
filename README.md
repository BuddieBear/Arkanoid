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
________________________________________
Giới thiệu chung
Arkanoid là một trò chơi hành động – phản xạ thuộc thể loại Breakout cổ điển, nơi người chơi điều khiển paddle để đánh bóng (ball) phá các viên gạch (bricks).
Phiên bản Arkanoid này được phát triển bằng Java 17 và JavaFX, bổ sung nhiều tính năng hiện đại và phong phú.
Các tính năng chính:
•	Nhiều màn chơi (Levels): Có độ khó tăng dần (ví dụ: STAGE_1, STAGE_2, STAGE_3).
•	Các loại gạch:
o	NormalBrick: Gạch thường, có thể bị phá.
o	IndestructibleBrick: Gạch không thể phá.
•	Hệ thống Power-Up & De-Buff: 9 loại vật phẩm đa dạng, có thời gian hiệu lực giới hạn.
•	Âm thanh chân thực: Bao gồm va chạm tường, gạch, paddle; âm thanh khi mất mạng hoặc nhận vật phẩm.
•	Chế độ chơi: 1 người chơi. Game có Đa luồng.

•	Hệ thống điểm & mạng: Ghi nhận Score, Lives và lưu High Score.
•	Tính năng đặc biệt: Người chơi có thể bắn đạn (Ammo) bằng cách nhấn chuột.
________________________________________
Giao diện người dùng
1. Màn hình chính:
•	Menu: Giao diện chính của trò chơi.
•	LevelPlay: Chọn màn chơi.
•	LoadScreen: Màn hình tải game.
•	Setting: Cài đặt game.
•	Instruction / Help: Hướng dẫn người chơi.
•	Power_Up_View: Hiển thị thông tin các vật phẩm.
2. Giao diện trong game:
•	Hiển thị điểm (Score) và mạng (Lives).
•	Khi nhận Power-Up, hiển thị thanh thời gian hiệu lực của vật phẩm đó.
________________________________________
Cấu trúc màn chơi (Level Design)
•	Gồm nhiều màn chơi với bố cục gạch khác nhau.
•	Dữ liệu màn chơi được tải từ các tệp bản đồ sử dụng:
•	MapLoader.loadBricksFromTiled();
________________________________________
Hệ thống gạch (Bricks)
Phân loại:
•	NormalBrick: Gạch thường, có thể bị phá.
•	IndestructibleBrick: Gạch không thể phá.
Điểm số:
•	Mỗi viên gạch bị phá cho điểm theo công thức:
•	Score += brick.getMaxHp() * 10
________________________________________
Hệ thống Power-Up
Game có 9 loại vật phẩm, chia thành 2 nhóm:
1. Buffs:
•	SuperBallPowerUp: bóng buff kích thước, speed.
•	InvincibleBallPowerUp: Bóng bất tử, phá gạch không nảy lại.
•	MultiBallPowerUp: Sinh thêm bóng phụ.
•	ExtraLifePowerUp: Cộng thêm 1 mạng.
•	DoubleScorePowerUp: Nhân đôi điểm nhận được.
•	RespawnFreePowerUp: (Thêm 1 lần respawn)
•	ExtendPaddle: Mở rộng kích thước paddle.
2. De-Buffs:
•	HarderBrickPowerDown: Gạch trở nên khó phá hơn.
•	ShrinkPaddle: Thu nhỏ paddle.
3. Cơ chế:
•	Power-Up rơi ra ngẫu nhiên khi gạch bị phá (với một tỉ lệ nhất định).
•	Mỗi Power-Up có thời gian hiệu lực nhất định (ví dụ: 3 giây).
•	Khi hiệu ứng đang hoạt động, hiển thị thanh thời gian (progress bar) trên giao diện.

- Qua mỗi khoảng thời gian có Boss và Lightning xuất hiện, khi Boss xuất hiện có các brick di chuyển xuống dưới tấn công Paddle.
________________________________________
Hệ thống âm thanh (Audio)
Game sử dụng lớp AudioSet để quản lý toàn bộ âm thanh:
•	Âm va chạm:
o	wallBounceSound: Bóng chạm tường.
o	collisionBrickSound: Bóng chạm gạch.
o	collisionPaddleSound: Bóng chạm paddle.
•	Âm hiệu ứng:
o	powerUpSound: Nhận Power-Up.
o	lossHpSound: Mất mạng.
o	gameOverSound: Khi Game Over.
________________________________________
Cơ chế gameplay
1. Cơ bản:
•	Điều khiển paddle bằng phím A (Trái) và D (Phải).
•	Nhấn Space để phóng bóng.
•	Bóng rơi khỏi màn hình → mất 1 mạng.
•	Hết tất cả mạng → Game Over.
•	Phá hết gạch có thể phá → Thắng màn chơi.
•	Nhấn Escape để tạm dừng game.
2. Cơ chế điểm & mạng:
•	Mỗi màn chơi có số mạng khởi đầu khác nhau (ví dụ: Màn 1: 5 mạng, Màn 2: 8 mạng).
•	Game lưu lại Highest Score đạt được.
3. Cơ chế đặc biệt:
•	Bắn đạn (Ammo): Nhấn chuột để bắn, mỗi lần bắn trừ 20 điểm.
•	Chế độ AI: Nhấn phím S để bật/tắt chế độ AI tự động điều khiển paddle.
________________________________________
Chế độ chơi
1. Chế độ 1 người:
•	Hiển thị paddle, bóng, gạch, điểm, mạng.
•	Mục tiêu: đạt điểm cao nhất có thể.
2. Chế độ AI:
•	Kích hoạt bằng phím S.
•	Paddle tự động di chuyển theo vị trí của bóng.

Nếu bị stuck bấm phím R để trở về tuy nhiên mất 1 mạng.
________________________________________
Cấu trúc chương trình
•	GameManager:
Lớp Application chính của JavaFX, quản lý vòng lặp game (AnimationTimer), trạng thái game (GameState), xử lý input (phím, chuột).
•	GameSetup:
Quản lý khởi tạo màn chơi, danh sách bricks, balls, paddles; xử lý logic điểm, mạng, điều kiện thắng/thua.
•	Ball:
Kế thừa MovableObject. Quản lý di chuyển, va chạm với gạch/paddle/tường, góc bắn.
•	Paddle:
Kế thừa MovableObject. Quản lý di chuyển, có thể dùng MovementStrategy (người chơi hoặc AI).
Boss: spawn boss để warning rằng có brick rơi xuống, logic của Brick rơi thì ở GameManager, GameSetup.
Lightning: spawn mây và thunder, mây để warning thunder xuất hiện tại đấy.
•	Brick:
Lớp cha cho các loại gạch, quản lý độ cứng (hitPoints).
•	PowerUp:
Lớp cha cho tất cả vật phẩm, quản lý thời gian hiệu lực (effectDurationMillis) và hiệu ứng.
•	GameView (và các View khác):
Quản lý render hình ảnh, giao diện, thanh điểm và vật phẩm.
________________________________________
GameView (và các lớp View khác): Quản lý việc vẽ (render) các đối tượng và giao diện lên GraphicsContext (Canvas). 

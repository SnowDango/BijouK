<br>
<br>

# BijouKとは

![airplane](%s)

<p>BijouK(ビジューク)とは、AppleMusicのカスタムクライアントである
<a href="https://cider.sh">Cider</a>
をリモートで操作するためのアプリです。</p>
<br>

## Deviceの追加方法

### 自分で入力する

- CiderでTokenを作成する (Token作成方法は後述)
- Devicesタブをタップしていただき、＋ボタンをタップして、Createをタップしてください。
- 表示されたダイアログに入力してください。
  Nameに管理するホストの名前（自由に付けてください）、Hostにアドレス（IPアドレスまたはホスト名）、Portにポート番号（デフォルトは10767）を入力してください。
  Tokenには、Ciderで作成したTokenを入力してください。
- Testをタップしていただき、接続できたら追加をタップしてください。

### QRを読み取る

- Ciderでremote app用のQR Codeを表示して下さい。（表示方法は後述）
- Devicesタブをタップしていただき、＋ボタンをタップして、QR Scanをタップしてください。
- Ciderで表示したQRを読みこんでください。
- 表示されたダイアログに管理するホストの名前（自由に付けてください）を入力してください。
- Saveボタンをタップし、追加してください。

## CiderのToken作成方法

ciderを起動し、設定をクリックし、Connectivityをクリックしてください。
Manage External Application Access to CiderのManageをクリックしてください。
Create Newをクリックして作成してください。

## CiderのRemote App用QR codeの表示方法

ciderを起動し、HelpからConnect a Remote Appをクリックしてください。
Remote NameにTokenの名前（自由に付けてください）を入力してください。
Create QR CodeをクリックしてQRcodeを表示してください。

<br>
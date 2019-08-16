# sprintReminder
n日おきに進捗報告をSlackに流すBot

## 使い方
1. コンパイルする(OpenJDK8以降をおすすめします)
1. jarを適当な場所に移動
1. jarと同じディレクトリにresourcesディレクトリを作成
1. resourcesディレクトリ内にsettingファイルを作成
1. setting内にSlack Incoming WebhookのURLを「https://hooks.slack.com/services/XXXXXXX/YYYYYYYY/ZZZZZZZZZZZZZZZ」とかく
1. java -jar (最後に報告をした日付、なければ今日) (報告の間隔) (今までの報告回数、現状利用してないので０でOKです) ./sprintReminder.jarで実行
1. nohup等で常駐させて利用してください

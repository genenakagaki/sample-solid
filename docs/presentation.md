# クオリティーの高いコードかどうかの判断方法

- 目的
  - 問題
  - 解決方法
- SOLIDの紹介
  - 紹介の流れ
  - Digital図書館(Kindle的な)
  - Single Responsibility Principle
  - Open-Closed Principle
  - Liskov Substitution Principle
  - Interface Segregation Principle
  - Dependency Inversion Principle

## 目的
今回の共有での範囲を明確にします。
どこまで説明してどこから自分でやってもらうか

### 問題
機能追加と機能修正をするのが難しい(先人の知恵が必要。時間がかかる。影響がわからない)
- わかりにくいコード
  - コードを理解するのに時間がかかる
  - 影響範囲がわからない
- どこまで影響が出るのかわからない

### 解決方法
上の問題が起きない良いデザインのコードを書く

でもそれは難しすぎるのでSOLIDを紹介します。

理想はこれですが、問題が起きない良いデザインのコードを書くのは難しいです。
良いデザインのコードを書けるようになるまで
- 良いデザインの理解
  - 感覚でわかる
  - 良いデザインかどうかの判断ができる
- 実際に良いデザインのコードを書く
  - 環境の条件

SOLID原則を紹介します。

### Disclaimer
Just because a new code SOLID, does't mean that you should use that code.
There are a lot of stuff in SOLID, so please discuss with your team
and decide which feature, component is the most important

for example, in vertical group, we put the importance in this order.
because...

## SOLIDの紹介

### 紹介の流れ
サンプルプロジェクトを実装していく感じになります。
最初にすごいシンプルなSolutionからはじめ、SOLID原則を一つづつ導入していって
良いコードになっていくのを一緒に見よう

「わかりにくい、変更しづらい -> 良くなったね」のパターンの繰り返しをします。

### Digital図書館の仕様説明

### Dependency Inversion Principle


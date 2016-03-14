(ns yahtzee.game-sequence)

(defprotocol GameSequence
  "How the game is played"
  (play [this] "play"))

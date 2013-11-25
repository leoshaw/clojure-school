;; Start a thread (future) to sum all the numbers to one hundred million
(def future-sum
  (future
    (reduce + (range 100000000))))

;; Create a function which appends the numbers 1-10 to a vector in an atom (hint: dotimes)
(defn append-to [buffer]
  (dotimes [n 10]
    (swap! buffer 
      (fn [old-buffer]
        (conj old-buffer (inc n))))))

;; Improved version of the above without unnecessary anonymous function
(defn append-to [buffer]
  (dotimes [n 10]
    (swap! buffer conj (inc n))))

;; Run the function across 10 threads simultaneously and observe the output
(def buffer (atom []))

(dotimes [_ 10]
  (future
    (append-to buffer)))

;; Extra credit: write a function that confirms you have ten of each number
(frequencies @buffer)

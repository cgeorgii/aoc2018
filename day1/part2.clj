(ns aoc-day1)

(def input "./input.txt")
(defn lazy-file-lines [file]
  (letfn [(helper [rdr]
            (lazy-seq
             (if-let [line (.readLine rdr)]
               (cons line (helper rdr))
               (do (.close rdr) nil))))]
    (helper (clojure.java.io/reader file))))

(def freqs (cycle (map read-string (lazy-file-lines input))))

(defn detect-repeated-freq [freqs]
  (letfn [(iter [result-set freqs last-result]
            (let [resulting-freq (+ (first freqs) last-result)]
              (if (contains? result-set resulting-freq)
                resulting-freq
                (recur (conj result-set resulting-freq) (rest freqs) resulting-freq))))]
    (iter #{} freqs 0)))

(println (detect-repeated-freq freqs))

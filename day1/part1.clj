(ns aoc-day1)

(def input "./input.txt")
(defn lazy-file-lines [file]
  (letfn [(helper [rdr]
            (lazy-seq
             (if-let [line (.readLine rdr)]
               (cons line (helper rdr))
               (do (.close rdr) nil))))]
    (helper (clojure.java.io/reader file))))

(def freqs (map read-string (lazy-file-lines input)))

(defn calibrate [freqs]
  (reduce + freqs))

(println (calibrate freqs))

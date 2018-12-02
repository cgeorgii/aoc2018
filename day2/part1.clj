(ns aoc-day2-part1)

(def input "./input.txt")

(defn lazy-file-lines [file]
  (letfn [(helper [rdr]
            (lazy-seq
             (if-let [line (.readLine rdr)]
               (cons line (helper rdr))
               (do (.close rdr) nil))))]
    (helper (clojure.java.io/reader file))))

(def ids (lazy-file-lines input))

(defn unique-occurrence-map [str]
  (vals (frequencies str)))

(defn exactly-2? [str]
  (some #{2} (unique-occurrence-map str)))

(defn exactly-3? [str]
  (some #{3} (unique-occurrence-map str)))

(defn checksum [input]
  (let [frequencies {3 (count (filter exactly-3? input))
                     2 (count (filter exactly-2? input))}]
    (* (get frequencies 3) (get frequencies 2))))

(println (checksum ids))

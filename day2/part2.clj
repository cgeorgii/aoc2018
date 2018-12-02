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

(defn distance [str1 str2]
  (count (filter false? (map #(= %1 %2) str1 str2))))

(defn find-similar [ids]
  (for [id1 ids id2 ids :when (and (not (= id1 id2)) (= 1 (distance id1 id2)))]
       (first (list id1 id2))))

(defn common-letters [id1 id2]
  (map #(if (= %1 %2) %1 "") id1 id2))

(def answer
  (clojure.string/join (apply common-letters (find-similar ids))))

(println answer)

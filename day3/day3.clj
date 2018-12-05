(ns aoc-day3)

(def input "./input.txt")

(defn lazy-file-lines [file]
  (letfn [(helper [rdr]
            (lazy-seq
             (if-let [line (.readLine rdr)]
               (cons line (helper rdr))
               (do (.close rdr) nil))))]
    (helper (clojure.java.io/reader file))))

(def lines (lazy-file-lines input))

(defn get-id [line]
  (first (clojure.string/split line #" ")))

(defn get-coordinates [line]
  (map #(Integer. %)
       (map #(clojure.string/replace % #":" "")
            (clojure.string/split
             (->> (clojure.string/split line #" ")
                  (drop 2)
                  (first))
             #","))))

(defn get-sizes [line]
  (map #(Integer. %)
       (clojure.string/split
        (->> (clojure.string/split line #" ")
             (last))
        #"x")))

(def claims
  (map #(hash-map :id (get-id %)
                  :coordinates (get-coordinates %)
                  :sizes (get-sizes %))
   lines))

(defn claimed-coords [claim]
  (let [begin-x (first (:coordinates claim))
        size-x (first (:sizes claim))
        end-x (+ begin-x size-x)
        begin-y (last (:coordinates claim))
        size-y (last (:sizes claim))
        end-y (+ begin-y size-y)]
    (for [x (range begin-x end-x)
          y (range begin-y end-y)
          :let [id (str x ":" y)]]
      id)))

(def claimed-cloth
 (frequencies (mapcat claimed-coords claims)))

(def answer
  (count
   (filter #(> % 1)
           (map val claimed-cloth))))

(defn overlap? [claim]
  (not (every? #(= 1 (get claimed-cloth %)) (claimed-coords claim))))

(def answer2
  (:id (first (filter #(not (overlap? %)) claims))))

(println (str "Total inches overlapped: " answer))
(println (str "Claim with no overlaps: " answer2))

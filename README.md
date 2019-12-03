<p align="right">
بِسْــــــــــــــمِ اللَّهِ الرَّحْمَنِ الرَّحِيم 
</p>

# Final Project Kecerdasan Buatan

Final project ini menggunakan bahasa pemrogaman java. 
<b>Please read this first !!</b>
[For Your Information](https://github.com/afrizal423/final-project-Kecerdasan-Buatan/blob/master/README.md#fyi)
[Need To Know](https://github.com/afrizal423/final-project-Kecerdasan-Buatan/blob/master/README.md#need-to-know)
## Kelompok
- Muhammad Iqbal D F
- Afrizal Muhamad Yasin
- Muhammad Rafi' Ramadhani
- Surya Adi Laksono


#### Algoritma yang dipakai
Algoritma Heuristik 3 A*

## Algoritma
Disini akan membahas algoritma yang berupa pseudocode beserta penjelasan sedikit.
### Heuristik 3
```pseudocode
function wrongAboveAdjacent (tiles of current state) returns number of wrongly adjacent tiles
	wrongAboveAdjacentTiles = 0;
	for row = 0 to 4:
		for col = 0 to 4:
			if (tiles[row][col] is 0) then
				continue;
			if (row is 0) then
				if (tiles[row][col] != row * 5 + col +1) then
					wrongAboveAdjacentTiles++;
			else if (tiles[previousRow][col] + 5 != tiles[row][col]) then
					wrongAboveAdjacentTiles++;
	return wrongAboveAdjacentTiles;

```
#### Penjelasan H3
Fungsi wrongAboveAdjacent mengambil array dua dimensi yang merepresentasikan tiles sebagai argumen dan mengembalikan jumlah total tiles yang salah  berdekatan di state. Fungsi yang pertama menginisialisasi nol bernilai salah pada AboveAdjacentTiles yang digunakan sebagai counter/penghitung. Kemudian akan nge-loop setiap baris dan kolom untuk memeriksa setiap tiles. Jika fungsi menemukan tiles yang kosong, itu akan hanya dilewati, jika tidak, fungsi akan memeriksa apakah saat ini sedang memeriksa tiles baris pertama, itu akan memeriksa tiles terhadap goal nilai tujuan, selain itu, nomor dalam baris lain akan diperiksa apakah mereka berdekatan dengan tiles di atas. Akhirnya, fungsi mengembalikan nilai wrongAboveAdjacentTiles sebagai indikator untuk inform search.

#### Implementasi java H3

```java
public static int wrongAboveAdjacent(int[][] states) {
    int wrongAboveAdjacentTiles = 0;
    for (int row = 0; row < 5; row++) {
   	for (int col = 0; col < 5; col++) {
    		//Ignore blank tiles
    		if (states[row][col] == 0)
    			continue;
    		if (row == 0) {
    			//Check first row tiles against goal state value
    			if (states[row][col] != row * 5 + col + 1)
    				wrongAboveAdjacentTiles++;
    			//other rows' tile checks against its above adjacent tile
    			} else if (states[row-1][col] + 5 != states[row][col])
    				wrongAboveAdjacentTiles++;
    	}
    }
    return wrongAboveAdjacentTiles;
 }

```

### A*
```pseudocode
function A*Search(Initial State) returns actions
	goal = genericSearch(Initial State, priority queue with A*Comparator);
	return Actions that lead from initial state to goal;

A*Comparator
function compare(nodeA, nodeB) returns integer value
	integer = (heuristic(state of nodeA) + totalPathCost(nodeA))
    – (heuristic(state of nodeB) + totalPathCost(nodeB));
	return integer;

function genericSearch(initial state, priorityQueue with defined comparator) returns Goal node
add initial state into priority queue;
while priority queue is not empty:
	head = first element(highest priority) in priority queue;
	if (head represents goal state) then
		return head;
	add all head’s expandable child to priority queue;
return null;

```

#### Penjelasan A*
Pertama, pencarian A * selanjutnya akan memanggil fungsi genericSearch untuk mencari ke state tujuan dengan memberikan kondisi awal dan antrian prioritas yang kosong dengan komparator yang ditentukan, yang merupakan Pembanding A * dalam kasus ini. A*Comparator digunakan oleh antrian prioritas untuk menentukan prioritas semua elemennya, dibedakan dengan nilai heuristiknya, yang merupakan pengembalian nilai dengan jumlah nilai fungsi heuristik dan total biaya jalur yang mengarah ke perpindahan. 
Saat berada di dalam fungsi genericSearch, pertama, ia akan memasukkan keadaan awal ke dalam antrian prioritas, dan loop untuk mencari ke status tujuan dengan memperluas semua gerakan yang mungkin dari gerakan saat ini sampai antrian prioritas kosong atau keadaan tujuan ditemukan. Null akan dikembalikan jika loop selesai tetapi status tujuan tidak ditemukan.

#### Implementasi java A*
```java
/**
 * A* implementation with heuristic function 2.
 * @param state initial puzzle state
 * @return moves that lead to goal state
 */
 public static Action[] solveH2A(PuzzleState state){
  /*  call generic search by given initial state and priority queue with 
 	[h(n1) + g(n1)] - [h(n2) + g(n2)] comparator. 
  */
  Node goal = Node.genericSearch(state, new PriorityQueue<Node>(11, 
    	new Comparator<Node>() {
    		//priority queue with comparator( [h(a) + g(a)] - [h(b) + g(b)] )	
    		public int compare(Node lhs,Node rhs) {
    			int lhsH2 = PuzzleState.mDistance(lhs.getState().getTiles());
    			int rhsH2 = PuzzleState.mDistance(rhs.getState().getTiles());
    			return (lhsH2 + lhs.getDepth()) - (rhsH2 + rhs.getDepth());
    		} 
    	}
  ));
  Action[] actions = goal.getActions();
  return actions;
}

```
```java
/**
 * Generic search function takes initial state and a priority queue as argument.
 * Node will be inserted into queue based on their priority, particularly their
 * heuristic value. The smaller heuristic value the node has, the higher 
 * priority the node has in the queue.Hence, a generic search can be implemented 
 * for both greedy search and A* search algorithm, but have to distinguish by 
 * their heuristic value. As given below:
 * Greedy Search: f(n) = h(n)
 * A* search: f(n) = h(n) + g(n)
 * @param initial	initial state
 * @param fringe	the pqueue of all nodes that should be expanded, usually
 * empty.
 * @return Node	the solution node if one is found, null otherwise
 */
 public static Node genericSearch(State initial, PriorityQueue<Node> fringe) {
    	fringe.add(new Node(initial));
    	while(!fringe.isEmpty()) {
    		//pick the lowest heuristic value move
    		Node head = fringe.poll(); 
    		State state = head.getState();
    		//check whether it is a goal state
    		if(state.goal()){
    			return head;
    		}
    		for (Object child:Arrays.asList(head.expand())) {
    				fringe.add((Node)child); 		
    		}
    	}
    	return null;
 }

```

## Cara Pakai

- Download / Clone repository ini
- jalankan menggunakan IDE, disini saya menggunakan eclipse

Note:
Jika hasil run kosong pada console. Silahkan run kembali, atau hapus cache/temp pada system (jika menggunakan windows, disini saya menggunakan aplikasi Wise Care 365)

## FYI
Dalam tahap awal di repo ini adalah kosongan, yang berarti masih ori kode dari yang diberikan oleh dosen. Setelah kelompok kami mendemokan hasilnya, baru saya akan membuat branch baru untuk hasil akhirnya.

## Need To Know
Program ini sepenuhnya diambil dari salah satu user Github. Jadi yang kami kerjakan disini, kami modifikasi code yang ada didalam fungsinya. <b>Perlu dikatahui juga</b> bahwasanya <i><b> kami masih menghormati orang yang membuat program ini.</i></b> Maka dari itu <b> Support Original Code</b>

## Sumber dan output
Dalam hal ini saya telah menemukan sumber dari github lain. jadi saya dan kita kelompok mengusahakan berbeda kodenya namun tetap hasil seperti dalam diinginkan. Jika ingin menggunakan sumber yang sama, tolong dukungannya untuk tidak persis sama dari yang sumbernya.
(untuk sumber silahkan cari sendiri, beberapa keyword dari readme ini pasti kalian akan cari di search engine)

### Out yang diinginkan
```
Initial state:
 02 03 04 07
 01 05 06 00
 09 10 11 08
 13 14 15 12

Solution via H3 with A*:-------------
1: UP
 02 03 04 00
 01 05 06 07
 09 10 11 08
 13 14 15 12

2: LEFT
 02 03 00 04
 01 05 06 07
 09 10 11 08
 13 14 15 12

3: LEFT
 02 00 03 04
 01 05 06 07
 09 10 11 08
 13 14 15 12

4: LEFT
 00 02 03 04
 01 05 06 07
 09 10 11 08
 13 14 15 12

5: DOWN
 01 02 03 04
 00 05 06 07
 09 10 11 08
 13 14 15 12

6: RIGHT
 01 02 03 04
 05 00 06 07
 09 10 11 08
 13 14 15 12

7: RIGHT
 01 02 03 04
 05 06 00 07
 09 10 11 08
 13 14 15 12

8: RIGHT
 01 02 03 04
 05 06 07 00
 09 10 11 08
 13 14 15 12

9: DOWN
 01 02 03 04
 05 06 07 08
 09 10 11 00
 13 14 15 12

10: DOWN
 01 02 03 04
 05 06 07 08
 09 10 11 12
 13 14 15 00

H3
Total langkah A*: 10
Nilai Effective Branching Factor(EBF): 1.047

```

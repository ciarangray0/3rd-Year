takes(tom, ct331).
takes(mary, ct331).
takes(joe, ct331).
takes(tom, ct345).
takes(mary, ct345).
instructs(bob, ct331).
instructs(ann, ct345).

teaches(Teacher, Student):-takes(Student, Z), instructs(Teacher, Z).

classmates(S1, S2):-takes(S1, Z), takes(S2, Z).
# 01. 티켓 판매 애플리케이션 구현하기

- 이벤트 당첨 관람객 - 초대장을 티켓으로 교환
    - 이벤트 당첨여부 확인
- 이벤트 낙첨 관람객 - 티켓을 구매
    - 티켓을 판매한 후 입장

### 필요한 클래스

- 이벤트 당첨자 발송 초대장
    
    ```java
    public class Invitation {
    	private LocalDateTime when;
    }
    ```
    
- 티켓 클래스
    
    ```java
    public class Ticket {
    	private Long fee;
    	
    	public Long getFee() {
    		return fee;
    	}
    }
    ```
    
- 관람객의 소지품 보관 클래스
    
    ```java
    public class Bag {
    	private Long amount;
    	private Invitation invitation;
    	private Ticket ticket;
    	
    	public boolean hasInvitation() {
    		return invitation != null;
    	}
    	
    	public boolean hasTicket() {
    		return ticket != null;
    	}
    	
    	public void setTicket(Ticket ticket) {
    		this.ticket = ticket;
    	}
    	
    	public void minuAmount(Long amount) {
    		this.amount += amount;
    	}
    }
    ```
    
    - Bag 생성자 추가
        - Bag 인스턴스를 생성하는 시점에 제약을 강제
        - 왜? → 현금과 초대장이 들어있거나, 현금만 들어있거나 둘 중 하나이기 때문
        
        ```java
        public class Bag {
        	public Bag(long amount) {
        		this(null, amount);
        	}
        	
        	public Bag(long amount, Invitation invitation) {
        		this.amount = amount;
        		this.invitation = invitation;
        	}
        }
        ```
        
- 청중 클래스 생성
    
    ```java
    public class Audience {
    	private Bag bag;
    	
    	public Audience(Bag bag) {
    		this.bag = bag;
    	}
    	
    	public Bag getBag() {
    		return bag;
    	}
    }
    ```
    
- 매표소 클래스 생성
    
    ```java
    public class TicketOffice {
    	// 판매할 티켓
    	private List<Ticket> tickets = new ArrayList<>();
    	// 판매 금액
    	private Long amount;
    	
    	// 매표소 생성자
    	public TicketOffice(Long amount, Ticket ... tickets) {
    		this.amount = amount;
    		this.tickets.addAll(Arrays.asList(tickets));
    	}
    	
    	// 티켓 판매
    	public Ticket getTicket() {
    		return tickets.remove(0);
    	}
    	// 판매 금액 계산
    	public void plusAmount(Long amount) {
    		this.amount += amount;
    	}
    	public void minusAmount(Long amount) {
    		this.amount -= amount;
    	}
    }
    ```
    
- 매표소 직원 클래스 생성
    
    ```java
    public class TicketSeller {
    	private	TicketOffice ticketoffice;
    	
    	public TicketSeller(TicketOffice ticketOffice) {
    		this.ticketOffice = ticketOffice;
    	}
    	
    	public TicketOffice getTicketOffice() {
    		return ticketOffice;
    	}
    }
    ```
    

### 판매 로직 구성

- 소극장 클래스 생성
    
    ```java
    public class Theater {
    	private TicketSeller ticketSeller;
    	
    	public Theater(TickerSeller ticketSeller) {
    		this.ticketSeller = ticketSeller;
    	}
    	
    	public void enter(Audience audience) {
    		if(audience.getBag().hasInvitation()) {
    			Ticket ticket = ticketSeller.getTicketOffice().getTicket();
    			audience.getBag().setTicket(ticket);
    		} else {
    			Ticket ticket = ticketSeller.getTicketOffice().getTicket();
    			audience.getBag().minusAmount(ticket.getFee());
    			ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
    			audience.getBag().setTicket(ticket);
    		}
    	}
    }
    ```
    

⇒ 로직은 간단하며 예상대로 동작한다. 하지만 몇 가지 문제점이 존재한다
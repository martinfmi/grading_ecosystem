class Message < ActiveRecord::Base
  validates_presence_of :subject, :body
  
  def deliver
    if Rails.env == "development"
      self.emails_sent = User.first.email
      UserMails.notification([User.first], self).deliver
    else
      emails_left = emails_to_send = User.all.map(&:email).select { |e| e =~ /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i }
      
      # Send the emails in chunks of 10. We don't want to overload the mail
      # server
      while emails_left && !emails_left.empty?
        self.emails_sent = emails_left[0, 10].join(', ')
        UserMails.notification(User.all, self).deliver
        
        emails_left = emails_left[10..-1]
      end
      
      self.emails_sent = emails_to_send
    end
    
    save
  end
end
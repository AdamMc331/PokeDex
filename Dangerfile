# Ignore inline messages that are outside of current diff
github.dismiss_out_of_range_messages

message "Thanks @#{github.pr_author}!"

# Report inline ktlint issues
checkstyle_format.base_path = Dir.pwd
checkstyle_format.report 'scripts/ktlint-report.xml'

# Notify of outdated dependencies in app
update_count = File.readlines("app/build/dependencyUpdates/report.txt").select { |line| line =~ /->/ }.count
if update_count > 10
  # More than 10 libraries to update is cumbersome in a comment, so summarize
  warn "There are #{update_count} dependencies with new milestone versions in app module."
elsif update_count > 0
  file = File.open("app/build/dependencyUpdates/report.txt", "rb").read
  heading = "The following app module dependencies have later milestone versions:"
  warn file.slice(file.index(heading)..-1)
end